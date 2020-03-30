package com.bridgelabz.utility;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStatesCode;
import com.bridgelabz.model.USCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusLoader {

    //READING STATE CENSUS DATA FROM CSV FILE
    public  HashMap<String, CensusDAO> loadStateCensusData(HashMap<String, CensusDAO> censusDAOMap, String... csvPath)
            throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            Iterable<CSVStateCensus> csvStateCensusIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStateCensusIterable.spliterator(),false)
                    .map(CSVStateCensus.class::cast)
                    .forEach(csvStateCensus -> censusDAOMap.put(csvStateCensus.getState(),new CensusDAO(csvStateCensus)));
            if (csvPath.length == 1)
                return censusDAOMap;
            return loadStateCodeData(censusDAOMap, csvPath[1]);
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READING STATE CODE DATA FROM CSV FILE
    public HashMap<String, CensusDAO> loadStateCodeData(HashMap<String, CensusDAO> censusDAOMap, String csvPath)
            throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStatesCode> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesCode.class);
            Iterable<CSVStatesCode> csvStateCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStateCodeIterable.spliterator(),false)
                    .map(CSVStatesCode.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.getStateName(),new CensusDAO(csvStateCode)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READING US CENSUS DATA FROM CSV FILE
    public HashMap<String, CensusDAO> loadUSCensusData(HashMap<String, CensusDAO> censusDAOMap, String... csvPath)
            throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, USCensus.class);
            Iterable<USCensus> usCensusIterable = () -> csvFileIterator;
            StreamSupport.stream(usCensusIterable.spliterator(), false)
                    .map(USCensus.class::cast)
                    .forEach(usCensus -> censusDAOMap.put(usCensus.getState(), new CensusDAO(usCensus)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }
}