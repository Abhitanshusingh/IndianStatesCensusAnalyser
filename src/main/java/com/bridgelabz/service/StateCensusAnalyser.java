package com.bridgelabz.service;

import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.model.CSVStatesCode;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class StateCensusAnalyser
{
    int countRecord = 0;

    //READING AND PRINTING DATA FROM CSV FILE
    public int loadCensusCsvData(String SAMPLE_CSV_PATH) throws CSVBuilderException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_PATH));)
        {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCensus> censusCSVList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
            return censusCSVList.size();
        }
        catch (IOException e)
        {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        }
        catch (RuntimeException e)
        {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READING AND PRINTING DATA FROM CSV FILE
    public int loadSateCodeCsvData(String CSV_PATH) throws CSVBuilderException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH));)
        {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStatesCode> censusCSVList = csvBuilder.getCSVFileList(reader, CSVStatesCode.class);
            return censusCSVList.size();
        }
        catch (IOException e)
        {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.FILE_NOT_FOUND, e.getMessage());
        }
        catch (RuntimeException e)
        {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //READ FILE EXTENSION
    public void getFileExtension(File file) throws CSVBuilderException
    {
        String extension = "";
        if (file != null)
        {
            String name = file.getName();
            extension = name.substring(name.lastIndexOf("."));
            if (!extension.equals(".csv"))
            {
                throw new CSVBuilderException
                        (CSVBuilderException.ExceptionType.ENTER_WRONG_TYPE, "FILE_TYPE_INCORRECT");
            }
        }
    }
}