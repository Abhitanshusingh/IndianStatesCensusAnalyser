package com.bridgelabz.service.test;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.service.StateCensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static com.bridgelabz.service.StateCensusAnalyser.COUNTRY.INDIA;
import static com.bridgelabz.service.StateCensusAnalyser.COUNTRY.US;

public class StateCensusAnalyserTest {
    //CREATED OBJECT OF SATAE CENSUS ANALYSER CALSS
    StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();
    //CSV STATE CENSUS DATA PATH
    private final String SIMPLE_CSV_PATH = "./src/test/resources/StateCensusData.csv";
    private final String INCORRECT_CSV_PATH = "/home/bridgelabz/Desktop/JavaProgram";
    private final String INCORRECT_CSV_TYPE_PATH = "./src/test/resources/StateCensusData.pdf";
    private final String INCORRECT_DELIMITER_PATH = "./src/test/resources/StateCensusData1.csv";
    private final String INCORRECT_HEADER_PATH = "./src/test/resources/StateCensusData2.csv";
    //CSV STATE CODE PATH
    private final String CSV_STATE_CODE_PATH = "./src/test/resources/StateCode.csv";
    private final String INCORRECT_CSV_STATE_CODE_PATH = "/resources/StateCode";
    private final String INCORRECT_EXTENSION_CSV_STATE_CODE = "./src/test/resources/StateCode.pdf";
    private final String INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH = "./src/test/resources/StateCode1.csv";
    private final String INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH = "./src/test/resources/StateCode 2.csv";
    //CSV US CENSUS PATH
    private final String CSV_US_CENSUS_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(INDIA, INCORRECT_CSV_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenTypeIncorrect_ShouldThrowCustomException() {
        try {
            File fileExtension = new File(INCORRECT_CSV_TYPE_PATH);
            censusAnalyser.getFileExtension(fileExtension);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.ENTER_WRONG_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenDelimiterIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(INDIA, INCORRECT_DELIMITER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(INDIA, INCORRECT_HEADER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH, CSV_STATE_CODE_PATH);
        Assert.assertEquals(37, totalRecords);
    }


    @Test
    public void givenStateCodeCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(INDIA, INCORRECT_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTypeIncorrect_ShouldThrowCustomException() {
        try {
            File fileExtension = new File(INCORRECT_EXTENSION_CSV_STATE_CODE);
            censusAnalyser.getFileExtension(fileExtension);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.ENTER_WRONG_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCode_WhenDelimiterIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(INDIA, INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(INDIA, INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList1() {
        try {
            censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Madhya Pradesh", censusCSV[13].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertNotEquals("Maharashta", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFilee_WhenSortedOnStateCode_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH, CSV_STATE_CODE_PATH);
            String sortedStateCodeData = censusAnalyser.getStateCodeWiseSortedData();
            CensusDAO[] stateCodes = new Gson().fromJson(sortedStateCodeData, CensusDAO[].class);
            Assert.assertEquals("AD", stateCodes[0].stateCode);
            Assert.assertEquals("WB", stateCodes[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenSortedImproperOnStateCode_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(INDIA, CSV_STATE_CODE_PATH);
            String sortedStateCodeCensusData = censusAnalyser.getStateCodeWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCodeCensusData, CensusDAO[].class);
            Assert.assertNotEquals("Madhya Pradesh", stateCensusesCSV[0].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedStateCensusData = censusAnalyser.getStateCensusPopulationWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensusesCSV[0].state);
            Assert.assertEquals("Sikkim", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulationDensity_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedStateCensusData = censusAnalyser.getStateCensusPopulationDensityWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, CensusDAO[].class);
            Assert.assertEquals("Bihar", stateCensusesCSV[0].state);
            Assert.assertEquals("Arunachal Pradesh", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnLargestArea_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedStateCensusData = censusAnalyser.getStateCensusLargestAreaWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", stateCensusesCSV[0].state);
            Assert.assertEquals("Goa", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = censusAnalyser.loadCensusData(US, CSV_US_CENSUS_PATH);
        Assert.assertEquals(51, totalRecords);
    }

    @Test
    public void givenUSCensusCsvFile_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(US, CSV_US_CENSUS_PATH);
            String sortedCensusData = censusAnalyser.getUSCensusPopulationWiseSortedData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("California", censusCSV[0].state);
            Assert.assertEquals("Wyoming", censusCSV[50].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCsvFile_WhenSortedImproperOnPopulation_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadCensusData(US, CSV_US_CENSUS_PATH);
            String sortedCensusData = censusAnalyser.getUSCensusPopulationWiseSortedData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertNotEquals("Ney York", censusCSV[33].state);
            Assert.assertNotEquals("Virginia", censusCSV[47].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }
}