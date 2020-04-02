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

import com.anji.integration.AnjiNetTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.nn.AnjiNet;
import java.util.Set;
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
            AnjiNetTranscriber transcriber = new AnjiNetTranscriber();
            AnjiNet net = transcriber.newAnjiNet(target.getAlleles(), "NULL");
            
            for (var out : net.getOutputNeurons()) {
                long id = out.getId(); // Is this the innovation id?
                
                // Get every connection that leads to out
            }
        } catch(TranscriberException ex) {
            throw new InvalidConfigurationException(ex.toString());
        }
    }
    
}
