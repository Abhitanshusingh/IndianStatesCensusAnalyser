package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class CSVStatesCode {
    //COLUMN NAME OF StateCensusCode
    @CsvBindByName(column = "SrNo")
    private Integer SrNo;

    @CsvBindByName(column = "StateName")
    private String StateName;

    @CsvBindByName(column = "TIN")
    private Integer TIN;

    @CsvBindByName(column = "StateCode")
    private String StateCode;

    //GETTER SETTER METHOD
    public void setSrNo(Integer srNo) {
        SrNo = srNo;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public void setTIN(Integer TIN) {
        this.TIN = TIN;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }

    public Integer getSrNo() {
        return SrNo;
    }

    public String getStateName() {
        return StateName;
    }

    public String getStateCode() {
        return StateCode;
    }

    public Integer getTIN() {
        return TIN;
    }
}
