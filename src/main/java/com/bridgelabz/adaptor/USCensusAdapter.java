package com.bridgelabz.adaptor;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.dto.USCensus;

import java.util.HashMap;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public HashMap<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
        return super.loadCensusData(USCensus.class, csvFilePath[0]);
    }
}