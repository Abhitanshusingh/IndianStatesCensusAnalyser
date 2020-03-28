package com.bridgelabz.dao;

import com.bridgelabz.model.USCensus;

public class USCensusDAO {
    public String stateID;
    public String state;
    public Long population;
    public Double area;
    public Float populationDensity;

    public USCensusDAO(USCensus usCensus) {
        stateID = usCensus.getStateID();
        state = usCensus.getState();
        population = usCensus.getPopulation();
        area = usCensus.getArea();
        populationDensity = usCensus.getPopulationDensity();
    }
}
