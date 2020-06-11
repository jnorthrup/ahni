/*
 *   YAHNI Yet Another HyperNEAT Implementation
 *   Copyright (C) 2020  Christian Lins <christian@lins.me>
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.lins.yahni.experiments;

import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.util.Configurable;
import com.anji.util.Properties;
import com.ojcoleman.ahni.hyperneat.HyperNEATEvolver;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import me.lins.yahni.neat.TrainingData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.jgapcustomised.BulkFitnessFunction;
import org.jgapcustomised.Chromosome;

/**
 * Bulk fitness functions are used to determine how optimal a group of solutions
 * are relative to each other. Bulk fitness functions can be useful (vs. normal
 * fitness functions) when fitness of a particular solution cannot be easily
 * computed in isolation, but instead is dependent upon the fitness of its
 * fellow solutions that are also under consideration. This abstract class
 * should be extended and the <code>evaluate(List)</code> method implemented to
 * evaluate each of the Chromosomes given in an array and set their fitness
 * values prior to returning.
 */
public class OWASClassifierFitnessFunction 
        extends BulkFitnessFunction 
        implements Configurable, TrainingData 
{
    
    private static final Logger LOGGER = Logger.getLogger(OWASClassifierFitnessFunction.class);
    
    private ActivatorTranscriber activatorFactory;
    private boolean endRun;
    private final Random random = new Random(0);
    private boolean targetFitnessMAE = false;
    
    private final List<List<double[]>> evalInputData = new ArrayList<>();
    private final List<List<double[]>> evalOutputData = new ArrayList<>();
    private final List<List<double[]>> inputData = new ArrayList<>();
    private final List<List<double[]>> outputData = new ArrayList<>();
    private final List<double[]> balancedInput = new ArrayList<>();
    private final List<double[]> balancedOutput = new ArrayList<>();
    
    public OWASClassifierFitnessFunction() {
    }
    
    @Override
    public void init(Properties properties) {
        activatorFactory = (ActivatorTranscriber) properties.singletonObjectProperty(ActivatorTranscriber.class);
        
        targetFitnessMAE = properties.getBooleanProperty("owas-classifier-neat-lins.target.mae", targetFitnessMAE);
        
        // Load the training data
        List<String> trainingFiles = 
                Arrays.asList(properties.getStringArrayProperty("training.file"));
        String[] inputCols  = properties.getStringArrayProperty("training.inputColumns");
        String[] outputCols = properties.getStringArrayProperty("training.outputColumns");
        
        long seed = properties.getLongProperty("random.seed", System.currentTimeMillis());
        if (seed != 0) {
            random.setSeed(seed);
        }
        
        // Shuffle the training files
        Collections.shuffle(trainingFiles, random);
        int numEvalFiles = Math.round(
                trainingFiles.size() * 
                properties.getFloatProperty("training.evalSplit", 0.1f)
        );
        System.out.println("Chosing " + numEvalFiles + " files as evaluation data.");
        
        for (var i = 0; i < trainingFiles.size(); i++) {
            String trainingFile = trainingFiles.get(i);
             
            List<double[]> oneSubjInput  = new ArrayList<>();
            List<double[]> oneSubjOutput = new ArrayList<>();
            
            try {
                Reader in = new FileReader(trainingFile);
                Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
                for (CSVRecord record : records) {
                    var input = new double[inputCols.length];
                    for (var n = 0; n < input.length; n++) {
                        input[n] = Double.parseDouble(record.get(inputCols[n]));
                    }
                    oneSubjInput.add(input);
                    
                    var output = new double[outputCols.length];
                    for (var n = 0; n < output.length; n++) {
                        output[n] = Double.parseDouble(record.get(outputCols[n]));
                    }
                    oneSubjOutput.add(output);
                }
            } catch (IOException ex) {
                LOGGER.warn("Error reading training data", ex);
                System.out.println(ex.getLocalizedMessage());
            }
            
            if (i <= numEvalFiles) {
                LOGGER.info("Eval file " + i + ": " + trainingFile);
                System.out.println("Eval file " + i + ": " + trainingFile);
                evalInputData.add(oneSubjInput);
                evalOutputData.add(oneSubjOutput);
            } else {
                inputData.add(oneSubjInput);
                outputData.add(oneSubjOutput);
            }
            
        }
        
        balanceData(inputData, outputData);
        
        LOGGER.info("OWASClassifierFitnessFunction initialized.");
    }
    
    private static void addTo(double[] a, double[] b) {
        for (int n = 0; n < a.length; n++) {
            a[n] += b[n];
        }
    }
    
    private static double balance(double[] classes) {
        double diff = 0;
        double sum = classes[0];
        for (int i = 1; i < classes.length; i++) {
            diff += Math.abs(classes[i] - classes[i - 1]);
            sum += classes[i];
        }
        
        return diff / sum;
    }
    
    private void balanceData(List<List<double[]>> input, List<List<double[]>> output) {       
        var classes = new double[output.get(0).get(0).length];
        int minSubjSamples = input.get(0).size();
        
        // How many samples do we have for each subject?
        for (var subj : input) {
            minSubjSamples = Math.min(minSubjSamples, subj.size());
        }
        
        // We can randomly sample minSubjSamples from each subject,
        // we start with half of it and leave the rest for balancing
        for (int s = 0; s < input.size(); s++) {
            var subjInput  = input.get(s);
            var subjOutput = output.get(s);
            
            for (int i = 0; i < minSubjSamples / 2; i++) {
                int r = random.nextInt(subjInput.size());
                var sampleInput  = subjInput.get(r);
                var sampleOutput = subjOutput.get(r);
                addTo(classes, sampleOutput);
                balancedInput.add(sampleInput);
                balancedOutput.add(sampleOutput);
            }
        }
       
        // The idea is to randomly sample data from each subject and check if the
        // sample reduces the imbalance. If this is the case then add the sample
        // to the data set.
        
        var balanceVTR = 0.01; // Minimal 1% relative error
        var balance = balance(classes);
        var tries = 100000; // Sanity check
        
        System.out.println("Balancing training data...");
        while(balance > balanceVTR && tries-- > 0) {
            System.out.println("Balance relative variance is " + balance);
            for (int subj = 0; subj < input.size(); subj++) {
                // Choose random sample
                int r = random.nextInt(input.get(subj).size());
                var sampleInput = input.get(subj).get(r);
                var sampleOutput = output.get(subj).get(r);
                
                if (balancedInput.contains(sampleInput)) {
                    continue;
                }
                
                var newClasses = classes.clone();
                addTo(newClasses, sampleOutput);
                
                var newBalance = balance(newClasses);
                
                if (newBalance < balance) {
                    balancedInput.add(sampleInput);
                    balancedOutput.add(sampleOutput);
                    balance = newBalance;
                    classes = newClasses;
                }
            }
        }
    }
    
    /**
     * Calculates and sets the fitness values on each of the given Chromosomes
     * via their setFitnessValue() method. May also set the performance of a
     * Chromosome if this is calculated independently of fitness.
     *
     * @param subjects {@link Chromosome} objects for which the fitness values
     * must be computed and set.
     */
    @Override
    public void evaluate(List<Chromosome> subjects) {
        endRun = false;
        
        subjects.parallelStream().forEach((chrome) -> {
            try {
                Activator activator = activatorFactory.newActivator(chrome);
                
                double avgerr = 0;
                int correct = 0;
                
                for(var n = 0; n < balancedInput.size(); n++) {
                    double[] result = activator.next(balancedInput.get(n));
                    double[] reference = balancedOutput.get(n);
                    avgerr += aggSquaredDiff(result, reference);
                    if (getIndexOfLargest(result) == getIndexOfLargest(reference))
                        correct++;
                }
                
                double fitness = (double)correct / balancedInput.size(); 
                double fitness_mae = 1 - avgerr / balancedInput.size();
                // TODO Which one is correct?
                if (targetFitnessMAE) {
                    fitness = fitness_mae;
                }
                chrome.setFitnessValue(fitness);
                chrome.setFitnessValue(fitness, 0);
                chrome.setPerformanceValue(fitness);
            } catch(TranscriberException ex) {
                LOGGER.warn("TranscriberException", ex);
            }
        });
    }
    
    public static int getIndexOfLargest(double[] array) {
        if (array == null || array.length == 0) {
            return -1; // null or empty
        }
        int largest = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[largest]) {
                largest = i;
            }
        }
        return largest; // position of the first largest found
    }

    
    private static String compareResults(double[] result, double[] reference) {
        int a = getIndexOfLargest(result);
        int b = getIndexOfLargest(reference);
        if (a < 0 && b < 0) {
            return "NA";
        } else if (a == b) {
            return "1";
        } else {
            return "0";
        }
    }
    
    /**
     * Evaluates the given chromosome against the evaluation data and stores
     * the result in a CSV file.
     * @param chrome 
     */
    public void evaluateReal(Chromosome chrome, PrintWriter out) {
        evaluateWithData(chrome, out, evalInputData, evalOutputData);
    }
    
    public void evaluateTraining(Chromosome chrome, PrintWriter out) {
        List<List<double[]>> _in = new ArrayList<>();
        _in.add(balancedInput);
        List<List<double[]>> _out = new ArrayList<>();
        _out.add(balancedOutput);
        evaluateWithData(chrome, out, _in, _out);
    }
    
    public void evaluateWithData(Chromosome chrome, PrintWriter out, 
            List<List<double[]>> input, List<List<double[]>> output) 
    {
        try {
            Activator activator = activatorFactory.newActivator(chrome);

            for (int s = 0; s < input.size(); s++) {
                var subjInput  = input.get(s);
                var subjOutput = output.get(s);
                
                for (var n = 0; n < subjInput.size(); n++) {
                    double[] result = activator.next(subjInput.get(n));
                    double[] reference = subjOutput.get(n);
                    
                    // We have class probabilities here but at last we need
                    // a decision for one class. The class with highest probability
                    // is the searched for class.
                    String c = compareResults(result, reference);
                    for(var d : reference) {
                        out.print(d);
                        out.print(", ");
                    }
                    for(var d : result) {
                        out.print(d);
                        out.print(", ");
                    }
                    out.println(c);
                }
            }
            
            out.flush();
        } catch (TranscriberException ex) {
            LOGGER.warn("TranscriberException", ex);
        } 
    }
    
    private double aggSquaredDiff(double[] a, double[] b) {
        assert a.length == b.length;
        
        double err = 0;
        for(var n = 0; n < a.length; n++) {
            double diff = a[n] - b[n];
            err = err + Math.sqrt(diff * diff);
        }
        return err / a.length;
    }

    @Override
    public boolean endRun() {
        return endRun;
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void evolutionFinished(HyperNEATEvolver evolver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<double[]> getInputData() {
        return balancedInput;
    }

    @Override
    public List<double[]> getOutputData() {
        return balancedOutput;
    }

}
