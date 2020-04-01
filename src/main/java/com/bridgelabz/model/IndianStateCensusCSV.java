package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class IndianStateCensusCSV {
    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population")
    public Long population;

    @CsvBindByName(column = "AreaInSqKm")
    public Double areaInSqKm;

    @CsvBindByName(column = "DensityPerSqkm")
    public Float densityPerSqkm;

    public String stateCode = new IndianStateCodeCSV().getStateCode();

    public IndianStateCensusCSV() {
    }

    public IndianStateCensusCSV(String state, String stateCode, Long population, Double areaInSqKm, Float densityPerSqkm) {
        this.stateCode = stateCode;
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqkm = densityPerSqkm;
    }

    @Override
    public String toString() {
        return "IndiaStateCensusCSV{" +
                "State='" + state + '\'' +
                ", Population=" + population +
                ", AreaInSqKm=" + areaInSqKm +
                ", DensityPerSqkm=" + densityPerSqkm +
                '}';
    }
}
