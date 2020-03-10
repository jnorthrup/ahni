/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anji.neat;

import java.util.Set;
import org.jgapcustomised.Allele;
import org.jgapcustomised.ChromosomeMaterial;
import org.jgapcustomised.Configuration;
import org.jgapcustomised.InvalidConfigurationException;
import org.jgapcustomised.MutationOperator;

/**
 *
 * @author cLins
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
        return;
    }
    
}
