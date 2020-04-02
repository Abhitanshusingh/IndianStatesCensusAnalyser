package com.bridgelabz.adaptor;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.dto.IndianStateCensusCSV;
import com.bridgelabz.dto.IndianStateCodeCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
    HashMap<String, CensusDAO> censusDAOHashMap = null;

    @Override
    public HashMap<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
        censusDAOHashMap = super.loadCensusData(IndianStateCensusCSV.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return censusDAOHashMap;
        return this.loadStateCodeData(censusDAOHashMap, csvFilePath[1]);
    }

    //READING DATA FROM STATE CODE CSV FILE
    public HashMap<String, CensusDAO> loadStateCodeData(HashMap<String, CensusDAO> censusDAOMap, String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            Iterator<IndianStateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCodeCSV.class);
            Iterable<IndianStateCodeCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .map(IndianStateCodeCSV.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.stateName, new CensusDAO(csvStateCode)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.FILE_NOT_FOUND,
                    e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER,
                    "Number of data fields does not match number of delimitr or headers.");
        }
    }
}
