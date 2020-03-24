package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class CSVStatesCode {
    //COLUMN NAME OF StateCensusCode
    @CsvBindByName
    private String SrNo;

    @CsvBindByName
    private String StateName;

    @CsvBindByName
    private String TIN;

    @CsvBindByName
    private String StateCode;

    //GETTER SETTER METHOD
    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }

    public String getSrNo() {
        return SrNo;
    }

    public String getStateName() {
        return StateName;
    }
}
