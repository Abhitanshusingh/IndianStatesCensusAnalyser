package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class USCensus {
    @CsvBindByName(column = "State Id")
    private String stateID;

    @CsvBindByName(column = "State")
    private String state;

    @CsvBindByName(column = "Population")
    private Long population;

    @CsvBindByName(column = "Total area")
    private Double area;

    @CsvBindByName(column = "Population Density")
    private Float populationDensity;

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateID() {
        return stateID;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getPopulation() {
        return population;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getArea() {
        return area;
    }

    public void setPopulationDensity(Float populationDensity) {
        this.populationDensity = populationDensity;
    }

    public Float getPopulationDensity() {
        return populationDensity;
    }
}
