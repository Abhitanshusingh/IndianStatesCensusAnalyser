package com.bridgelabz.service;

import com.bridgelabz.adaptor.CensusAdapter;
import com.bridgelabz.adaptor.CensusAdapterFactory;
import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class CensusAnalyser {
    HashMap<String, CensusDAO> censusDAOHashMap = new HashMap<String, CensusDAO>();

    public enum COUNTRY {INDIA, US}

    public enum SortingMode {AREA, STATE, STATECODE, DENSITY, POPULATION}

    private COUNTRY country;

    public CensusAnalyser(COUNTRY country) {
        this.country = country;
    }

    //GENERIC METHOD LOAD CSV FILE DATA
    public int loadCensusData(String... csvFilePath) throws CSVBuilderException {
        CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
        censusDAOHashMap = censusDataLoader.loadCensusData(csvFilePath);
        return censusDAOHashMap.size();
    }

    //READ FILE EXTENSION
    public void getFileExtension(File file) throws CSVBuilderException {
        String extension = "";
        if (file != null) {
            String name = file.getName();
            extension = name.substring(name.lastIndexOf("."));
            if (!extension.equals(".csv")) {
                throw new CSVBuilderException
                        (CSVBuilderException.ExceptionType.ENTER_WRONG_TYPE, "FILE_TYPE_INCORRECT");
            }
        }
    }

    //SORTING IN A JSON FORMATS TO STATE CENSUS DATA
    public String getSortedCensusData(SortingMode mode) throws CSVBuilderException {
        if (censusDAOHashMap == null || censusDAOHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        ArrayList censusDTO = censusDAOHashMap.values().stream()
                .sorted(CensusDAO.getSortComparator(mode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }
    //SORTING BOTH POPULATION AND DENSITY
    public String getDualSortByPopulationDensity() throws CSVBuilderException {
        if (censusDAOHashMap == null || censusDAOHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No Census Data");
        ArrayList censusDTO = censusDAOHashMap.values().stream()
                .sorted(Comparator.comparingInt(CensusDAO::getPopulation)
                        .thenComparingDouble(CensusDAO::getPopulationDensity).reversed())
                .map(c -> c.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }
}