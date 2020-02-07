/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anji.persistence;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects statistics about the evolution run, e.g. average/best/worst fitness
 * throughout the generations.
 * @author Christian Lins
 */
public class EvolverStatistics {
    
    private final List<GenerationStatistics> generations = new ArrayList<>();
            
    public GenerationStatistics newGeneration() {
        GenerationStatistics gen = new GenerationStatistics(generations.size());
        generations.add(gen);
        return gen;
    }
    
    public void print(PrintWriter out) {
        for (var gen : generations) {
            out.println(gen.toString());
        }
    }
}
