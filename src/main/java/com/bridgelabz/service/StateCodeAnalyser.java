package com.bridgelabz.service;
import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStatesCode;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCodeAnalyser {
    int countRecord = 0;

    //READING AND PRINTING DATA FROM CSV FILE
    public int loadCensusCsvData(String CSV_PATH) throws StateCensusAnalyserException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH));)
        {
            CsvToBean<CSVStatesCode> CsvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStatesCode.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStatesCode> csvUserIterator = CsvToBean.iterator();
            while (csvUserIterator.hasNext())
            {
                CSVStatesCode csvUser = csvUserIterator.next();
                System.out.println("SrNo : " + csvUser.getSrNo());
                System.out.println("SateName : " + csvUser.getStateName());
                System.out.println("TIN : " + csvUser.getTIN());
                System.out.println("StateCode : " + csvUser.getStateCode());
                System.out.println("<=========================>");
                countRecord++;
            }
            return countRecord;
        }
        catch (IOException e)
        {
            throw new StateCensusAnalyserException
                    (StateCensusAnalyserException.Exceptiontype.FILE_NOT_FOUND, e.getMessage());
        }
        catch (RuntimeException e)
        {
            throw new StateCensusAnalyserException
                    (StateCensusAnalyserException.Exceptiontype.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READ FILE EXTENSION
    public void getFileExtension(File file) throws StateCensusAnalyserException
    {
        String extension = "";
        if (file != null)
        {
            String name = file.getName();
            extension = name.substring(name.lastIndexOf("."));
            if (!extension.equals(".csv"))
            {
                throw new StateCensusAnalyserException
                        (StateCensusAnalyserException.Exceptiontype.ENTER_WRONG_TYPE, "FILE_TYPE_INCORRECT");
            }
        }
    }
}