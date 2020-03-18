package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;
//COLUMN NAME OF StateCensusAnalyserData
public class CSVStateCensus
{
    @CsvBindByName
    private String State;

    @CsvBindByName
    private String Population;

    @CsvBindByName
    private String AreaInSqKm;

    @CsvBindByName
    private String DensityPerSqkm;

    //GETTER SETTER METHOD
    public void setState(String state)
    {
        State = state;
    }

    public void setPopulation(String population)
    {
        Population = population;
    }

    public void setAreaInSqKm(String areaInSqKm)
    {
        AreaInSqKm = areaInSqKm;
    }

    public void setDensityPerSqkm(String densityPerSqkm)
    {
        DensityPerSqkm = densityPerSqkm;
    }

    public String getState()
    {
        return State;
    }

    public String getPopulation()
    {
        return Population;
    }

    public String getAreaInSqKm()
    {
        return AreaInSqKm;
    }

    public String getDensityPerSqkm()
    {
        return DensityPerSqkm;
    }
}
