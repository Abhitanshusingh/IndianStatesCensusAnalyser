package com.bridgelabz.service;

import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStatesCode;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;

public class StateCensusAnalyser {
    Collection<Object> CensusRecords = null;
    HashMap<Object, Object> CensusHashMap = null;

    //READING DATA FROM CSV FILE
    public int loadCsvData(String SAMPLE_CSV_PATH, Class csvClass) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_PATH));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            CensusHashMap = csvBuilder.getCSVFileMap(reader, csvClass);
            return CensusHashMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
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
        if (CensusHashMap == null || CensusHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<CSVStateCensus> censusCSVComparator = Comparator.comparing(census -> census.getState());
        this.sort(censusCSVComparator, CensusHashMap);
        CensusRecords = CensusHashMap.values();
        String sortedStateCensusJson = new Gson().toJson(CensusRecords);
        return sortedStateCensusJson;
    }

    //SORTING IN A JSON FORMATS TO STATE CODE DATA
    public String getStateCodeWiseSortedData() throws CSVBuilderException {
        if (CensusHashMap == null || CensusHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<CSVStatesCode> stateCodeCSVComparator = Comparator.comparing(stateCode -> stateCode.getStateCode());
        this.sort(stateCodeCSVComparator, CensusHashMap);
        CensusRecords = CensusHashMap.values();
        String sortedStateCodeJson = new Gson().toJson(CensusRecords);
        return sortedStateCodeJson;
    }

    //SORTING CSV FILE GENERIC METHOD
    public <E> void sort(Comparator<E> censusCSVComparator, Map<Object, Object> censusRecords) {
        for (int iterate = 0; iterate < censusRecords.size() - 1; iterate++) {
            for (int Inneriterate = 0; Inneriterate < censusRecords.size() - iterate - 1; Inneriterate++) {
                E census1 = (E) censusRecords.get(Inneriterate);
                E census2 = (E) censusRecords.get(Inneriterate + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusRecords.put(Inneriterate, census2);
                    censusRecords.put(Inneriterate + 1, census1);
                }
            }
        }
    }
}