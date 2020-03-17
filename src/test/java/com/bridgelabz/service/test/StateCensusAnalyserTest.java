package com.bridgelabz.service.test;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class StateCensusAnalyserTest {
    StateCensusAnalyser cencusAnalyser = new StateCensusAnalyser();
    private final String SIMPLE_CSV_PATH = "/home/bridgelabz/Desktop/JavaPrograms/IndianStateCensusAnalyser/src/test/resources/StateCensusData.csv";
    private final String INCORRECT_CSV_PATH = "/home/bridgelabz/Desktop/JavaProgram";
    private final String INCORRECT_CSV_TYPE = "/home/bridgelabz/Desktop/JavaPrograms/IndianStateCensusAnalyser/src/test/resources/StateCensusData.pdf";
    private final String INCORRECT_DELIMITER = "/home/bridgelabz/Desktop/JavaPrograms/IndianStateCensusAnalyser/src/test/resources/StateCensusData1.csv";

    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch()
            throws StateCensusAnalyserException {
        int totalRecords = cencusAnalyser.loadCensusCsvData(SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() {
        try {
            cencusAnalyser.loadCensusCsvData(INCORRECT_CSV_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.FILE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenTypeIncorrect_ShouldThrowCustomException() {
        try {
            File fileExtension = new File(INCORRECT_CSV_TYPE);
            cencusAnalyser.getFileExtension(fileExtension);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTER_WRONG_TYPE, "FILE TYPE INCORRECT");
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenDelimiterIncorrect_ShouldThrowCustomException() {
        try {
            cencusAnalyser.loadCensusCsvData(INCORRECT_DELIMITER);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.DELIMITER_INCORRECT, e.type);
        }
    }
}