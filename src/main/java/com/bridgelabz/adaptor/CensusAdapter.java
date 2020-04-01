package com.bridgelabz.adaptor;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.IndianStateCensusCSV;
import com.bridgelabz.model.IndianStateCodeCSV;
import com.bridgelabz.model.USCensus;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {
    HashMap<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();
    ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

    public abstract HashMap<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException;

    public <E> HashMap<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath[0]))) {
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().equals("com.bridgelabz.model.IndianStateCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndianStateCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO((IndianStateCensusCSV) censusCSV)));
            }
            if (censusCSVClass.getName().equals("com.bridgelabz.model.USCensus")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensus.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.FILE_NOT_FOUND,
                    e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER,
                    "Number of data fields does not match number of delimiter or headers.");
        }
    }
}
