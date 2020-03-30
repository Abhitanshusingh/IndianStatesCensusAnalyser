package com.bridgelabz.dao;

import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStatesCode;
import com.bridgelabz.model.USCensus;

public class CensusDAO {
    public String state;
    public Long areaInSqKm;
    public Integer densityPerSqKm;
    public Long population;
    public String stateCode;
    public Integer srNo;
    public Integer tin;
    public String stateID;
    public Double area;
    public Float populationDensity;

    public CensusDAO(CSVStateCensus csvStateCensus) {
        state = csvStateCensus.getState();
        areaInSqKm = csvStateCensus.getAreaInSqKm();
        densityPerSqKm = csvStateCensus.getDensityPerSqkm();
        population = csvStateCensus.getPopulation();
    }

    public CensusDAO(CSVStatesCode csvStateCode) {
        state = csvStateCode.getStateName();
        stateCode = csvStateCode.getStateCode();
        srNo = csvStateCode.getSrNo();
        tin = csvStateCode.getTIN();
    }

    public CensusDAO(USCensus usCensus) {
        stateID = usCensus.getStateID();
        state = usCensus.getState();
        population = usCensus.getPopulation();
        area = usCensus.getArea();
        populationDensity = usCensus.getPopulationDensity();
    }
}