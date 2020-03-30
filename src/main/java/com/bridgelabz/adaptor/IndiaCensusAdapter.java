package com.bridgelabz.adaptor;
import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStatesCode;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;
public class IndiaCensusAdapter extends CensusAdapter {
    @Override
    public  Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(CSVStateCensus.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return  censusStateMap;
        return this.loadStateCodeData(censusStateMap, csvFilePath[1]);
    }

    //READING DATA FROM STATE CODE CSV FILE
    public static Map<String, CensusDAO> loadStateCodeData(Map<String, CensusDAO> censusDAOMap, String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStatesCode> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesCode.class);
            Iterable<CSVStatesCode> csvStateCodeIterable = () -> csvFileIterator;
            StreamSupport.stream(csvStateCodeIterable.spliterator(), false)
                    .map(CSVStatesCode.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.getStateName(), new CensusDAO(csvStateCode)));
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
