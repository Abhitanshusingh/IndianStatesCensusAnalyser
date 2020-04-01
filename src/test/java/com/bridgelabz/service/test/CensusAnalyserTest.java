package com.bridgelabz.service.test;

import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.IndianStateCensusCSV;
import com.bridgelabz.model.USCensus;
import com.bridgelabz.service.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;


public class CensusAnalyserTest {
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

    CensusAnalyser indiaCensusAnalyser = new CensusAnalyser(CensusAnalyser.COUNTRY.INDIA);
    CensusAnalyser usCensusAnalyser = new CensusAnalyser(CensusAnalyser.COUNTRY.US);

    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            indiaCensusAnalyser.loadCensusData(INCORRECT_CSV_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenTypeIncorrect_ShouldThrowCustomException() {
        try {
            File fileExtension = new File(INCORRECT_CSV_TYPE_PATH);
            indiaCensusAnalyser.getFileExtension(fileExtension);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.ENTER_WRONG_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenDelimiterIncorrect_ShouldThrowCustomException() {
        try {
            indiaCensusAnalyser.loadCensusData(INCORRECT_DELIMITER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            indiaCensusAnalyser.loadCensusData(INCORRECT_HEADER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH, CSV_STATE_CODE_PATH);
        Assert.assertEquals(37, totalRecords);
    }


    @Test
    public void givenStateCodeCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            indiaCensusAnalyser.loadCensusData(INCORRECT_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTypeIncorrect_ShouldThrowCustomException() {
        try {
            File fileExtension = new File(INCORRECT_EXTENSION_CSV_STATE_CODE);
            indiaCensusAnalyser.getFileExtension(fileExtension);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.ENTER_WRONG_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCode_WhenDelimiterIncorrect_ShouldThrowCustomException() {
        try {
            indiaCensusAnalyser.loadCensusData(INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() {
        try {
            indiaCensusAnalyser.loadCensusData(INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            IndianStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianStateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList1() {
        try {
            indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            IndianStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianStateCensusCSV[].class);
            Assert.assertEquals("Madhya Pradesh", censusCSV[13].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATE);
            IndianStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianStateCensusCSV[].class);
            Assert.assertNotEquals("Maharashta", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenSortedOnStateCode_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH, CSV_STATE_CODE_PATH);
            String sortedStateCodeData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            IndianStateCensusCSV[] stateCodes = new Gson().fromJson(sortedStateCodeData, IndianStateCensusCSV[].class);
            Assert.assertEquals("AD", stateCodes[0].stateCode);
            Assert.assertEquals("WB", stateCodes[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenSortedImproperOnStateCode_ShouldNotReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(CSV_STATE_CODE_PATH);
            String sortedStateCodeCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.STATECODE);
            IndianStateCensusCSV[] stateCensusesCSV = new Gson().fromJson(sortedStateCodeCensusData, IndianStateCensusCSV[].class);
            Assert.assertNotEquals("Madhya Pradesh", stateCensusesCSV[0].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH);
            String sortedStateCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            IndianStateCensusCSV[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, IndianStateCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensusesCSV[0].state);
            Assert.assertEquals("Sikkim", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulationDensity_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH);
            String sortedStateCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.DENSITY);
            IndianStateCensusCSV[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, IndianStateCensusCSV[].class);
            Assert.assertEquals("Bihar", stateCensusesCSV[0].state);
            Assert.assertEquals("Arunachal Pradesh", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnLargestArea_ShouldReturnSortedList() {
        try {
            indiaCensusAnalyser.loadCensusData(SIMPLE_CSV_PATH);
            String sortedStateCensusData = indiaCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.AREA);
            IndianStateCensusCSV[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, IndianStateCensusCSV[].class);
            Assert.assertEquals("Rajasthan", stateCensusesCSV[0].state);
            Assert.assertEquals("Goa", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException {
        int totalRecords = usCensusAnalyser.loadCensusData(CSV_US_CENSUS_PATH);
        Assert.assertEquals(51, totalRecords);
    }

    @Test
    public void givenUSCensusCsvFile_WhenSortedOnPopulation_ShouldReturnSortedList() {
        try {
            usCensusAnalyser.loadCensusData(CSV_US_CENSUS_PATH);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            USCensus[] censusCSV = new Gson().fromJson(sortedCensusData, USCensus[].class);
            Assert.assertEquals("California", censusCSV[0].state);
            Assert.assertEquals("Wyoming", censusCSV[50].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCsvFile_WhenSortedImproperOnPopulation_ShouldNotReturnSortedList() {
        try {
            usCensusAnalyser.loadCensusData(CSV_US_CENSUS_PATH);
            String sortedCensusData = usCensusAnalyser.getSortedCensusData(CensusAnalyser.SortingMode.POPULATION);
            USCensus[] censusCSV = new Gson().fromJson(sortedCensusData, USCensus[].class);
            Assert.assertNotEquals("Ney York", censusCSV[33].state);
            Assert.assertNotEquals("Virginia", censusCSV[47].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }
}