package com.bridgelabz.exception;

public class CSVBuilderException extends Exception{
    public enum ExceptionType
    {
        FILE_NOT_FOUND,ENTER_WRONG_TYPE,INCORRECT_DELIMITER_OR_HEADER,ILLEGAL_STATE;
    }
    public ExceptionType type;

    public CSVBuilderException(ExceptionType type,String message)
    {
        super(message);
        this.type = type;
    }
}

