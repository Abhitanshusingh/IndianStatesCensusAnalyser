package com.bridgelabz.service.test;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class StateCensusAnalyserTest {
    StateCensusAnalyser cencusAnalyser = new StateCensusAnalyser();
    private final String SIMPLE_CSV_PATH = "./src/test/resources/StateCensusData.csv";
    private final String INCORRECT_CSV_PATH = "/home/bridgelabz/Desktop/JavaProgram";
    private final String INCORRECT_CSV_TYPE_PATH = "./src/test/resources/StateCensusData.pdf";
    private final String INCORRECT_DELIMITER_PATH = "./src/test/resources/StateCensusData1.csv";
    private final String INCORRECT_HEADER_PATH = "./src/test/resources/StateCensusData2.csv";

    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws StateCensusAnalyserException
    {
        int totalRecords = cencusAnalyser.loadCensusCsvData(SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException()
    {
        try
        {
            cencusAnalyser.loadCensusCsvData(INCORRECT_CSV_PATH);
        }
        catch (StateCensusAnalyserException e)
        {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.FILE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenTypeIncorrect_ShouldThrowCustomException()
    {
        try
        {
            cencusAnalyser.getFileExtension(new File(INCORRECT_CSV_TYPE_PATH));
        }
        catch (StateCensusAnalyserException e)
        {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTER_WRONG_TYPE,e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenDelimiterIncorrect_ShouldThrowCustomException()
    {
        try
        {
            cencusAnalyser.loadCensusCsvData(INCORRECT_DELIMITER_PATH);
        }
        catch (StateCensusAnalyserException e)
        {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.DELIMITER_INCORRECT,e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException()
    {
        try
        {
            cencusAnalyser.loadCensusCsvData(INCORRECT_HEADER_PATH);
        }
        catch (StateCensusAnalyserException e)
        {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.NO_SUCH_HEADER,e.type);
        }
    }

}