package com.bridgelabz.service;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilderImp<E> implements ICSVBuilder{
    //CREATE GENERIC METHOD TO ITERATE CSV FILE
    @Override
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws StateCensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            Iterator<E> censusCSVIterator = csvToBean.iterator();
            return censusCSVIterator;
        } catch (IllegalStateException e) {
            throw new StateCensusAnalyserException
                    (StateCensusAnalyserException.Exceptiontype.ILLEGAL_STATE,e.getMessage());
        }
    }
}
