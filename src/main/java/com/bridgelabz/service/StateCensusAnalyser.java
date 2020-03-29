package com.bridgelabz.service;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVStateCensus;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

import com.bridgelabz.service.ICSVBuilder;
import com.bridgelabz.service.CSVBuilderFactory;
import com.bridgelabz.model.CSVStatesCode;
import com.bridgelabz.model.USCensus;
import com.google.gson.Gson;

public class StateCensusAnalyser {
    Collection<CensusDAO> CensusRecords = null;
    HashMap<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();

    //READING STATE CENSUS DATA FROM CSV FILE
    public int loadStateCensusData(String csvPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            while (csvFileIterator.hasNext()) {
                CensusDAO indianCensusDAO = new CensusDAO(csvFileIterator.next());
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
            Iterable<CSVStatesCode> csvStatesCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStatesCodeIterable.spliterator(), false)
                    .map(CSVStatesCode.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.getStateName(), new CensusDAO(csvStateCode)));
            return censusDAOMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READING USCENSUS DATA FROM CSV FILE
    public int loadUSCensusData(String csvPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, USCensus.class);
            Iterable<USCensus> usCensusIterable = () -> csvFileIterator;
            StreamSupport.stream(usCensusIterable.spliterator(), false)
                    .map(USCensus.class::cast)
                    .forEach(usCensus -> censusDAOMap.put(usCensus.getState(), new CensusDAO(usCensus)));
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
        Comparator<Map.Entry<String, CensusDAO>> stateCensusComparator =
                Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusComparator);
        CensusRecords = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(CensusRecords);
        return sortedStateCensusJson;
    }

    //SORTING IN A JSON FORMATS TO STATE CODE DATA
    public String getStateCodeWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCodeCSVComparator =
                Comparator.comparing(stateCode -> stateCode.getValue().stateCode);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeCSVComparator);
        CensusRecords = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(CensusRecords);
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

    //SORTING CSV STATE CENSUS DATA POPULATION DENSITY WISE
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