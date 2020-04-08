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
package com.anji.neat;

import com.anji.integration.AnjiActivator;
import com.anji.integration.AnjiNetTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.nn.AnjiNet;
import com.anji.nn.Connection;
import com.anji.nn.Neuron;
import com.anji.nn.NeuronConnection;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import me.lins.yahni.neat.TrainingData;
import org.jgapcustomised.Allele;
import org.jgapcustomised.ChromosomeMaterial;
import org.jgapcustomised.Configuration;
import org.jgapcustomised.InvalidConfigurationException;
import org.jgapcustomised.MutationOperator;

/**
 *
 * @author Christian Lins
 */
public class BackpropagationOperator extends MutationOperator {

    public BackpropagationOperator() {
        super(1.0); // always
     }
    
    @Override
    protected void mutate(Configuration config, ChromosomeMaterial target, 
            Set<Allele> allelesToAdd, Set<Allele> allelesToRemove) 
                throws InvalidConfigurationException 
    {
        try {
            if (!(config.getBulkFitnessFunction() instanceof TrainingData))
                return;
            
            TrainingData data = (TrainingData)config.getBulkFitnessFunction();
            
            AnjiNetTranscriber transcriber = new AnjiNetTranscriber();
            AnjiNet net = transcriber.newAnjiNet(target.getAlleles(), "NULL");
            AnjiActivator activator = new AnjiActivator(net, 1);

            // Batch learning: we sum the weight changes for every data sample
            // for every connection and apply it afterwards
            Map<String, Double> dW = new HashMap<>();
            
            for (int n = 0; n < data.getInputData().size(); n++) {
                var input  = data.getInputData().get(n);
                var output = data.getOutputData().get(n);
                
                activator.next(input);

                for (int m = 0; m < net.getOutputNeurons().size(); m++) {
                    var neuron = net.getOutputNeuron(m);
                    Queue<Neuron> backPropQueue = new ArrayDeque<>();
                    
                    long id = neuron.getId(); // Is this the innovation id?
                    
                    // Overall error of the (output) neuron
                    double Etotal = output[m] - neuron.getValue();

                    // Get every connection that leads to this neuron
                    var conns = neuron.getIncomingConns();
                    for (Connection conn : conns) {
                        NeuronConnection nconn = (NeuronConnection)conn;
                        double w = nconn.getWeight();
                        
                        // Delta rule:
                        // δEtotal / δw = 
                        //       -(output[m] - neuron.getValue()) * deriv.act.f()
                        double a = 0.1; // Learning rate 
                        double dw = a * Etotal * 0; // delta w
                    }
                }
            }
        } catch(TranscriberException ex) {
            throw new InvalidConfigurationException(ex.toString());
        }
    }
    
}
