package com.bridgelabz.service;

import com.bridgelabz.adaptor.CensusAdapter;
import com.bridgelabz.adaptor.CensusAdapterFactory;
import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;

import java.io.File;
import java.util.*;

import com.bridgelabz.utility.CensusLoader;
import com.google.gson.Gson;

public class StateCensusAnalyser {
    CensusLoader censusLoader = new CensusLoader();
    Collection<CensusDAO> censusRecords = null;
    Map<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();

    public enum COUNTRY {INDIA, US}

    //GENERIC METHOD LOAD CSV FILE DATA
    public int loadCensusData(COUNTRY country, String... csvFilePath) throws CSVBuilderException {
        CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
        censusDAOMap = censusDataLoader.loadCensusData(csvFilePath);
        return censusDAOMap.size();
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
    public String getStateWiseSortedCensusData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusComparator =
                Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusComparator);
        censusRecords = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(censusRecords);
        return sortedStateCensusJson;
    }

    //SORTING IN A JSON FORMATS TO STATE CODE DATA
    public String getStateCodeWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCodeCSVComparator =
                Comparator.comparing(stateCode -> stateCode.getValue().stateCode);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeCSVComparator);
        censusRecords = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(censusRecords);
        return sortedStateCodeJson;
    }

    //SORTING CSV STATE CENSUS DATA POPULATION WISE
    public String getStateCensusPopulationWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusCSVComparator =
                Comparator.comparing(census -> census.getValue().population);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusCSVComparator);
        ArrayList<CensusDAO> sortedList = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(sortedList);
        String sortedStateCensusPopulationJson = new Gson().toJson(sortedList);
        return sortedStateCensusPopulationJson;
    }

    //SORTING CSV STATE CENSUS DATA POPULATION DENSITY WISE
    public String getStateCensusPopulationDensityWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusCSVComparator =
                Comparator.comparing(census -> census.getValue().densityPerSqKm);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusCSVComparator);
        ArrayList<CensusDAO> sortedList = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(sortedList);
        String sortedStateCensusPopulationJson = new Gson().toJson(sortedList);
        return sortedStateCensusPopulationJson;
    }

    //SORTING CSV STATE CENSUS DATA AREA
    public String getStateCensusLargestAreaWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusCSVComparator =
                Comparator.comparing(census -> census.getValue().areaInSqKm);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusCSVComparator);
        ArrayList<CensusDAO> sortedList = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(sortedList);
        String sortedStateCensusPopulationJson = new Gson().toJson(sortedList);
        return sortedStateCensusPopulationJson;
    }

    //SORTING CSV STATE CENSUS DATA POPULATION WISE
    public String getUSCensusPopulationWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> usCensusCSVComparator =
                Comparator.comparing(census -> census.getValue().population);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(usCensusCSVComparator);
        ArrayList<CensusDAO> sortedList = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(sortedList);
        String sortedStateCensusPopulationJson = new Gson().toJson(sortedList);
        return sortedStateCensusPopulationJson;
    }

    //SORTING CSV FILE GENERIC METHOD
    private <E extends CensusDAO> LinkedHashMap<String, CensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, CensusDAO>> entries = censusDAOMap.entrySet();
        List<Map.Entry<String, CensusDAO>> listOfEntries =
                new ArrayList<Map.Entry<String, CensusDAO>>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, CensusDAO> sortedByValue =
                new LinkedHashMap<String, CensusDAO>(listOfEntries.size());
        // COPING LIST TO MAP
        for (Map.Entry<String, CensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}