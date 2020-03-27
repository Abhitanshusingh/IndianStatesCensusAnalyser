package com.bridgelabz.service;

import com.bridgelabz.dao.IndianCensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVStateCensus;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.bridgelabz.model.CSVStatesCode;
import com.google.gson.Gson;

public class StateCensusAnalyser {
    Collection<com.bridgelabz.dao.IndianCensusDAO> CensusRecords = null;
    HashMap<String, IndianCensusDAO> censusDAOMap = new HashMap<String, IndianCensusDAO>();

    //READING STATE CENSUS DATA FROM CSV FILE
    public int loadStateCensusData(String csvPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            while (csvFileIterator.hasNext()) {
                IndianCensusDAO indianCensusDAO = new IndianCensusDAO(csvFileIterator.next());
                this.censusDAOMap.put(indianCensusDAO.state, indianCensusDAO);
            }
            return censusDAOMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READING STATE CODE DATA FROM CSV FILE
    public int loadStateCodeData(String csvPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStatesCode> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesCode.class);
            while (csvFileIterator.hasNext()) {
                IndianCensusDAO indianCensusDAO = new IndianCensusDAO(csvFileIterator.next());
                this.censusDAOMap.put(indianCensusDAO.stateCode, indianCensusDAO);
            }
            return censusDAOMap.size();
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
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, IndianCensusDAO>> stateCensusComparator =
                Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(stateCensusComparator);
        CensusRecords = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(CensusRecords);
        return sortedStateCensusJson;
    }

    //SORTING IN A JSON FORMATS TO STATE CODE DATA
    public String getStateCodeWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, IndianCensusDAO>> stateCodeCSVComparator =
                Comparator.comparing(stateCode -> stateCode.getValue().stateCode);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(stateCodeCSVComparator);
        CensusRecords = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(CensusRecords);
        return sortedStateCodeJson;
    }

    //SORTING CSV FILE GENERIC METHOD
    private <E extends IndianCensusDAO> LinkedHashMap<String, IndianCensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, IndianCensusDAO>> entries = censusDAOMap.entrySet();
        List<Map.Entry<String, IndianCensusDAO>> listOfEntries =
                new ArrayList<Map.Entry<String, IndianCensusDAO>>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue =
                new LinkedHashMap<String, IndianCensusDAO>(listOfEntries.size());
        // COPING LIST TO MAP
        for (Map.Entry<String, IndianCensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}