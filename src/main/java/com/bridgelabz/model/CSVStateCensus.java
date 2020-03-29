package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

//COLUMN NAME OF StateCensusAnalyserData
public class CSVStateCensus {
    @CsvBindByName(column = "State")
    private String State;

    @CsvBindByName(column = "Population")
    private Long Population;

    @CsvBindByName(column = "AreaInSqKm")
    private Long AreaInSqKm;

    @CsvBindByName(column = "DensityPerSqkm")
    private Integer DensityPerSqkm;

    //GETTER SETTER METHOD
    public void setState(String state) {
        State = state;
    }

    public void setPopulation(Long population) {
        Population = population;
    }

    public void setAreaInSqKm(Long areaInSqKm) {
        AreaInSqKm = areaInSqKm;
    }

    public void setDensityPerSqkm(Integer densityPerSqkm) {
        DensityPerSqkm = densityPerSqkm;
    }

    public String getState() {
        return State;
    }

    public Long getPopulation() {
        return Population;
    }

    public Long getAreaInSqKm() {
        return AreaInSqKm;
    }

    public Integer getDensityPerSqkm() {
        return DensityPerSqkm;
    }
}
