package com.bridgelabz.service.test;

import com.bridgelabz.dao.IndianCensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.service.StateCensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

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

    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadStateCensusData(INCORRECT_CSV_PATH);
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
            censusAnalyser.loadStateCensusData(INCORRECT_DELIMITER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadStateCensusData(INCORRECT_HEADER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = censusAnalyser.loadStateCodeData(CSV_STATE_CODE_PATH);
        Assert.assertEquals(37, totalRecords);
    }

    @Test
    public void givenStateCodeCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadStateCodeData(INCORRECT_CSV_STATE_CODE_PATH);
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
            censusAnalyser.loadStateCodeData(INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadStateCodeData(INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList1() {
        try {
            censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Madhya Pradesh", censusCSV[13].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertNotEquals("Maharashta", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFilee_WhenSortedOnStateCode_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadStateCodeData(CSV_STATE_CODE_PATH);
            String sortedStateCodeData = censusAnalyser.getStateCodeWiseSortedData();
            IndianCensusDAO[] stateCodes = new Gson().fromJson(sortedStateCodeData, IndianCensusDAO[].class);
            Assert.assertEquals("AD", stateCodes[0].stateCode);
            Assert.assertEquals("WB", stateCodes[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenSortedImproperOnStateCode_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadStateCodeData(CSV_STATE_CODE_PATH);
            String sortedStateCodeCensusData = censusAnalyser.getStateCodeWiseSortedData();
            IndianCensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCodeCensusData, IndianCensusDAO[].class);
            Assert.assertNotEquals("Madhya Pradesh", stateCensusesCSV[0].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulation_ShouldReturnSortedList()  {
        try {
            censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedStateCensusData = censusAnalyser.getStateCensusPopulationWiseSortedData();
            IndianCensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensusesCSV[0].state);
            Assert.assertEquals("Sikkim", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulationDensity_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedStateCensusData = censusAnalyser.getStateCensusPopulationDensityWiseSortedData();
            IndianCensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Bihar", stateCensusesCSV[0].state);
            Assert.assertEquals("Arunachal Pradesh", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnLargestArea_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedStateCensusData = censusAnalyser.getStateCensusLargestAreaWiseSortedData();
            IndianCensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Rajasthan", stateCensusesCSV[0].state);
            Assert.assertEquals("Goa", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }
}