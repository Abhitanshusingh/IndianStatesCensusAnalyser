package com.bridgelabz.service;

import com.bridgelabz.exception.CSVBuilderException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilderImp<E> implements ICSVBuilder{
    //CREATE GENERIC METHOD TO ITERATE CSV FILE
    @Override
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            Iterator<E> censusCSVIterator = csvToBean.iterator();
            return censusCSVIterator;
        } catch (IllegalStateException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.ExceptionType.ILLEGAL_STATE,e.getMessage());
        }
    }
}