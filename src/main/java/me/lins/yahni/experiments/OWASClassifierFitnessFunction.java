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
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
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
public class OWASClassifierFitnessFunction extends BulkFitnessFunction implements Configurable {
    
    private static final Logger LOGGER = Logger.getLogger(OWASClassifierFitnessFunction.class);
    
    private ActivatorTranscriber activatorFactory;
    private boolean endRun;
    
    private final List<double[]> inputData = new ArrayList<>();
    private final List<double[]> outputData = new ArrayList<>();
    
    public OWASClassifierFitnessFunction() {
    }
    
    @Override
    public void init(Properties properties) {
        activatorFactory = (ActivatorTranscriber) properties.singletonObjectProperty(ActivatorTranscriber.class);
        
        // Load the training data
        String[] trainingFiles = properties.getStringArrayProperty("training.file");
        String[] inputCols  = properties.getStringArrayProperty("training.inputColumns");
        String[] outputCols = properties.getStringArrayProperty("training.outputColumns");
        
        for (var trainingFile : trainingFiles) {
            try {
                Reader in = new FileReader(trainingFile);
                Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
                for (CSVRecord record : records) {
                    var input = new double[inputCols.length];
                    for (var n = 0; n < input.length; n++) {
                        input[n] = Double.parseDouble(record.get(inputCols[n]));
                    }
                    inputData.add(input);
                    
                    var output = new double[outputCols.length];
                    for (var n = 0; n < output.length; n++) {
                        output[n] = Double.parseDouble(record.get(outputCols[n]));
                    }
                    outputData.add(output);
                }
            } catch (IOException ex) {
                LOGGER.warn("Error reading training data", ex);
            }
        }
        LOGGER.info("OWASClassifierFitnessFunction initialized.");
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
        
        for(Chromosome chrome : subjects) {
            try {
                Activator activator = activatorFactory.newActivator(chrome);
                
                double avgerr = 0;
                
                for(var n = 0; n < inputData.size(); n++) {
                    double[] result = activator.next(inputData.get(n));
                    double[] reference = outputData.get(n);
                    avgerr += aggSquaredDiff(result, reference);
                }
                
                double fitness = 1 - avgerr / inputData.size();
                // TODO Which one is correct?
                chrome.setFitnessValue(fitness);
                chrome.setFitnessValue(fitness, 0);
                chrome.setPerformanceValue(fitness);
            } catch(TranscriberException ex) {
                LOGGER.warn("TranscriberException", ex);
            }
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

}
