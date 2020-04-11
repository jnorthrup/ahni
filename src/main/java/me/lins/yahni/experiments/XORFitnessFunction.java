/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.lins.yahni.experiments;

import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.util.Configurable;
import com.anji.util.Properties;
import com.ojcoleman.ahni.hyperneat.HyperNEATEvolver;
import java.util.ArrayList;
import java.util.List;
import me.lins.yahni.neat.TrainingData;
import org.apache.log4j.Logger;
import org.jgapcustomised.BulkFitnessFunction;
import org.jgapcustomised.Chromosome;

/**
 *
 * @author chris
 */
public class XORFitnessFunction 
        extends BulkFitnessFunction 
        implements Configurable, TrainingData
{
    private static final Logger LOGGER = Logger.getLogger(XORFitnessFunction.class);

    private ActivatorTranscriber activatorFactory;
    private boolean endRun;
    private List<double[]> input = new ArrayList<>();
    private List<double[]> output = new ArrayList<>();

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
    public void evaluate(List<Chromosome> subjects) {
        endRun = false;
        
        subjects.parallelStream().forEach((chrome) -> {
            try {
                Activator activator = activatorFactory.newActivator(chrome);
                
                double avgerr = 0;
                
                for(var n = 0; n < input.size(); n++) {
                    double[] result = activator.next(input.get(n));
                    double[] reference = output.get(n);
                    avgerr += aggSquaredDiff(result, reference);
                }
                
                double fitness = 1 - avgerr / input.size();

                // TODO Which one is correct?
                chrome.setFitnessValue(fitness);
                chrome.setFitnessValue(fitness, 0);
                chrome.setPerformanceValue(fitness);
            } catch(TranscriberException ex) {
                LOGGER.warn("TranscriberException", ex);
            }
        });    
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
    public void init(Properties props) throws Exception {
        activatorFactory = (ActivatorTranscriber) props.singletonObjectProperty(ActivatorTranscriber.class);
    
        input.add(new double[]{0,0}); output.add(new double[]{0});
        input.add(new double[]{0,1}); output.add(new double[]{1});
        input.add(new double[]{1,0}); output.add(new double[]{1});
        input.add(new double[]{1,1}); output.add(new double[]{0});
    }

    @Override
    public List<double[]> getInputData() {
        return input;
    }

    @Override
    public List<double[]> getOutputData() {
        return output;
    }
    
}
