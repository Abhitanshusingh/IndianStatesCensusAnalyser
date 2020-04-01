package com.bridgelabz.dao;

import com.bridgelabz.model.IndianStateCensusCSV;
import com.bridgelabz.model.IndianStateCodeCSV;
import com.bridgelabz.model.USCensus;
import com.bridgelabz.service.CensusAnalyser;

import java.util.Comparator;

public class CensusDAO {
    public String state;
    public Double areaInSqKm;
    public Float densityPerSqKm;
    public Long population;
    public String stateCode;
    public Integer srNo;
    public Integer tin;

    public CensusDAO(IndianStateCensusCSV indianStateCensusCSV) {
        state = indianStateCensusCSV.state;
        areaInSqKm = indianStateCensusCSV.areaInSqKm;
        densityPerSqKm = indianStateCensusCSV.densityPerSqkm;
        population = indianStateCensusCSV.population;
    }

    public CensusDAO(IndianStateCodeCSV csvStateCode) {
        state = csvStateCode.stateName;
        stateCode = csvStateCode.stateCode;
        srNo = csvStateCode.srNo;
        tin = csvStateCode.tin;
    }

    public CensusDAO(USCensus usCensus) {
        stateCode = usCensus.stateID;
        state = usCensus.state;
        population = usCensus.population;
        areaInSqKm = usCensus.area;
        densityPerSqKm = usCensus.populationDensity;
    }

    public CensusDAO(){ }

    public static Comparator<? super CensusDAO> getSortComparator(CensusAnalyser.SortingMode mode) {
        if (mode.equals(CensusAnalyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.state);
        if (mode.equals(CensusAnalyser.SortingMode.STATECODE))
            return Comparator.comparing(census -> census.stateCode);
        if (mode.equals(CensusAnalyser.SortingMode.POPULATION))
            return Comparator.comparing(CensusDAO::getPopulation).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.DENSITY))
            return Comparator.comparing(CensusDAO::getPopulationDensity).reversed();
        if (mode.equals(CensusAnalyser.SortingMode.AREA))
            return Comparator.comparing(CensusDAO::getTotalArea).reversed();
        return null;
    }

    private Double getTotalArea() {
        return areaInSqKm;
    }

    private Float getPopulationDensity() {
        return this.densityPerSqKm;
    }

    private Long getPopulation() {
        return this.population;
    }

    public Object getCensusDTO(CensusAnalyser.COUNTRY country) {
        if (country.equals(CensusAnalyser.COUNTRY.INDIA))
            return new IndianStateCensusCSV(state, stateCode, population, areaInSqKm, densityPerSqKm);
        if (country.equals(CensusAnalyser.COUNTRY.US))
            return new USCensus(state, stateCode, population, areaInSqKm, densityPerSqKm);
        return null;
    }
}