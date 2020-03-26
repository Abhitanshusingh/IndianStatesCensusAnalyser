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
    Collection<Object> stateCensusRecords = null;
    Collection<Object> stateCensusCodesRecord = null;
    HashMap<Object, Object> stateCodeHashMap = null;
    HashMap<Object, Object> stateCensusHashMap = null;

    //READING AND PRINTING DATA FROM CSV FILE
    public int loadCensusCsvData(String SAMPLE_CSV_PATH) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_PATH));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCensusHashMap = csvBuilder.getCSVFileMap(reader, CSVStateCensus.class);
            return stateCensusHashMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READING AND PRINTING DATA FROM CSV FILE
    public int loadSateCodeCsvData(String CSV_PATH) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCodeHashMap = csvBuilder.getCSVFileMap(reader, CSVStatesCode.class);
            return stateCodeHashMap.size();
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
        if (stateCensusHashMap == null || stateCensusHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<CSVStateCensus> censusCSVComparator = Comparator.comparing(census -> census.getState());
        this.sort(censusCSVComparator, stateCensusHashMap);
        stateCensusRecords =  stateCensusHashMap.values();
        String sortedStateCensusJson = new Gson().toJson(stateCensusRecords);
        return sortedStateCensusJson;
    }

    //SORTING IN A JSON FORMATS TO STATE CODE DATA
    public String getStateCodeWiseSortedData() throws CSVBuilderException {
        if (stateCodeHashMap == null || stateCodeHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<CSVStatesCode> stateCodeCSVComparator = Comparator.comparing(stateCode -> stateCode.getStateCode());
        this.sort(stateCodeCSVComparator, stateCodeHashMap);
        stateCensusCodesRecord =  stateCodeHashMap.values();
        String sortedStateCodeJson = new Gson().toJson(stateCensusCodesRecord);
        return sortedStateCodeJson;
    }

    //SORTING CSV FILE GENERIC METHOD
    public <E> void sort(Comparator<E> censusCSVComparator, Map<Object,Object> censusRecords) {
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