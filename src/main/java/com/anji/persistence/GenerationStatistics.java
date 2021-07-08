/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anji.persistence;

import java.util.List;
import org.jgapcustomised.Species;

/**
 *
 * @author Christian Lins
 */
public class GenerationStatistics {
    protected int generationNumber;
    protected int numIndividuals = -1;
    protected long fittestId = -1;
    protected long bestPerformingId = -1;
    protected double fitness = Double.NaN;
    protected double performance = Double.NaN;
    protected String species = null;
    
    public GenerationStatistics(int gn) {
        this.generationNumber = gn;
    }
    
    public void setFittest(long id, double fitness) {
        this.fittestId = id;
        this.fitness = fitness;
    }
    
    public void setBestPerforming(long id, double performance) {
        this.bestPerformingId = id;
        this.performance = performance;
    }
    
    public void setSpecies(List<Species> species) {
        var speciesStr = new StringBuilder();
        for(var specie : species) {
            speciesStr.append(specie.getID()).append(":");
            speciesStr.append(specie.getChromosomes().size()).append(";");
        }
        
        this.species = speciesStr.toString();
    }
    
    public String toStringHeader() {
        return "Generation, FittestID, Fitness, BestPerformingID, Performance, Species";
    }
    
    @Override
    public String toString() {
        var buf = new StringBuilder();
        
        buf.append(generationNumber).append(", ");
        buf.append(fittestId).append(", ");
        buf.append(fitness).append(", ");
        buf.append(bestPerformingId).append(", ");
        buf.append(performance).append(", ");
        buf.append(species);
        
        return buf.toString();
    }
}
