package com.bridgelabz.service.test;

import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
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
        int totalRecords = censusAnalyser.loadCensusCsvData(SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusCsvData(INCORRECT_CSV_PATH);
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
            censusAnalyser.loadCensusCsvData(INCORRECT_DELIMITER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusCsvData(INCORRECT_HEADER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = censusAnalyser.loadSateCodeCsvData(CSV_STATE_CODE_PATH);
        Assert.assertEquals(37, totalRecords);
    }

    @Test
    public void givenStateCodeCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadSateCodeCsvData(INCORRECT_CSV_STATE_CODE_PATH);
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
            censusAnalyser.loadSateCodeCsvData(INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            censusAnalyser.loadSateCodeCsvData(INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            censusAnalyser.loadCensusCsvData(SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].getState());
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList1() {
        try {
            censusAnalyser.loadCensusCsvData(SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Madhya Pradesh", censusCSV[13].getState());
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() {
        try {
            censusAnalyser.loadCensusCsvData(SIMPLE_CSV_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertNotEquals("Maharashta", censusCSV[0].getState());
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }
}