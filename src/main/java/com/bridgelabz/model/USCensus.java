package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class USCensus {
    @CsvBindByName(column = "State Id")
    public String stateID;

    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population")
    public Long population;

    @CsvBindByName(column = "Total area")
    public Double area;

    @CsvBindByName(column = "Population Density")
    public Float populationDensity;

    public USCensus() {
    }

    public USCensus(String state, String stateCode, Long population, Double areaInSqrKm, Float DensityPerSqKm) {
        this.stateID = stateCode;
        this.state = state;
        this.population = population;
        this.area = areaInSqrKm;
        this.populationDensity = DensityPerSqKm;
    }

    @Override
    public String toString() {
        return "USCensus{" +
                "stateID='" + stateID + '\'' +
                ", state='" + state + '\'' +
                ", population=" + population +
                ", area=" + area +
                ", populationDensity='" + populationDensity + '\'' +
                '}';
    }
}
