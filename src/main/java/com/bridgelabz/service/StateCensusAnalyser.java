package com.bridgelabz.service;
import com.bridgelabz.model.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {
    //TAKING PATH OF CSV FILE
    private static final String SAMPLE_CSV_PATH =
            "/home/bridgelabz/Desktop/JavaPrograms/IndianStateCensusAnalyser/src/test" +
                    "/resources/StateCensusData.csv";
    int countRecord = 0;

    //READING AND PRINTING DATA FROM CSV FILE
    public int loadCensusCsvData() throws IOException {
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
            return countRecord;
        }
    }
}