package com.bridgelabz.service.test;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class StateCensusAnalyserTest {
    StateCensusAnalyser cencusAnalyser = new StateCensusAnalyser();
    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws IOException {
       int totalRecords = cencusAnalyser.loadCensusCsvData();
        Assert.assertEquals(29,totalRecords);
    }
}
