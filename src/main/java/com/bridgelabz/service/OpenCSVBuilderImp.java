package com.bridgelabz.service;

import com.bridgelabz.exception.CSVBuilderException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OpenCSVBuilderImp<E> implements ICSVBuilder {

    //CREATE GENERIC METHOD TO ITERATE CSV FILE
    @Override
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException {
        try {
            CsvToBean<E> csvToBean = (CsvToBean<E>) this.getCSVBean(reader, csvClass);
            Iterator<E> censusCSVIterator = csvToBean.iterator();
            return censusCSVIterator;
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.ILLEGAL_STATE, e.getMessage());
        }
    }

    @Override
    public <E> List getCSVFileList(Reader reader, Class<E> csvClass) throws CSVBuilderException {
        return (List<E>) this.getCSVBean(reader, csvClass).parse();
    }

    @Override
    public <E> HashMap<E, E> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderException {
        List list = getCSVFileList(reader, csvClass);
        Map<Integer,Object> map = new HashMap<Integer, Object>();
        Integer count =0;
        for (Object ob:list) {
            map.put( count, ob);
            count++;
        }
        return (HashMap<E, E>) map;
    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.ILLEGAL_STATE, e.getMessage());
        }
    }
}