package com.bridgelabz.dao;

import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStatesCode;

public class IndianCensusDAO {
    public String state;
    public Double areaInSqKm;
    public Double densityPerSqKm;
    public Double population;
    public String stateName;
    public String stateCode;
    public Integer srNo;
    public Integer tin;

    public IndianCensusDAO(CSVStateCensus csvStateCensus) {
        state = csvStateCensus.getState();
        areaInSqKm = csvStateCensus.getAreaInSqKm();
        densityPerSqKm = csvStateCensus.getDensityPerSqkm();
        population = csvStateCensus.getPopulation();
    }

    public IndianCensusDAO(CSVStatesCode csvStateCode) {
        stateName = csvStateCode.getStateName();
        stateCode = csvStateCode.getStateCode();
        srNo = csvStateCode.getSrNo();
        tin = csvStateCode.getTIN();
    }
}