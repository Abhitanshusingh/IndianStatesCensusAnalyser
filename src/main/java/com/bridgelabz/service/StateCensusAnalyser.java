package com.bridgelabz.service;
import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {
    int countRecord = 0;
    //READING AND PRINTING DATA FROM CSV FILE
    public int loadCensusCsvData(String SAMPLE_CSV_PATH) throws StateCensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_PATH));) {
            CsvToBean<CSVStateCensus> CsvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStateCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStateCensus> csvUserIterator = CsvToBean.iterator();
            while (csvUserIterator.hasNext()) {
                CSVStateCensus csvUser = csvUserIterator.next();
                System.out.println("State : " + csvUser.getState());
                System.out.println("Population : " + csvUser.getPopulation());
                System.out.println("AreaInSqKm : " + csvUser.getAreaInSqKm());
                System.out.println("DensityPerSqkm : " + csvUser.getDensityPerSqkm());
                System.out.println("<=========================>");
                countRecord++;
            }
        }
         catch (IOException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.FILE_NOT_FOUND, e.getMessage());
        }
        return countRecord;
    }
}