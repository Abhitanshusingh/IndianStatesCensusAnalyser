package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

//COLUMN NAME OF StateCensusAnalyserData
public class CSVStateCensus {
    @CsvBindByName(column = "State")
    private String State;

    @CsvBindByName(column = "Population")
    private Double Population;

    @CsvBindByName(column = "AreaInSqKm")
    private Double AreaInSqKm;

    @CsvBindByName(column = "DensityPerSqkm")
    private Double DensityPerSqkm;

    //GETTER SETTER METHOD
    public void setState(String state) {
        State = state;
    }

    public void setPopulation(Double population) {
        Population = population;
    }

    public void setAreaInSqKm(Double areaInSqKm) {
        AreaInSqKm = areaInSqKm;
    }

    public void setDensityPerSqkm(Double densityPerSqkm) {
        DensityPerSqkm = densityPerSqkm;
    }

    public String getState() {
        return State;
    }

    public Double getPopulation() {
        return Population;
    }

    public Double getAreaInSqKm() {
        return AreaInSqKm;
    }

    public Double getDensityPerSqkm() {
        return DensityPerSqkm;
    }
}
