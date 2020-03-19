package com.bridgelabz.exception;

public class StateCensusAnalyserException extends Exception
{
    public enum Exceptiontype
    {
        FILE_NOT_FOUND,ENTER_WRONG_TYPE,INCORRECT_DELIMITER_OR_HEADER,ILLEGAL_STATE;
    }
   public Exceptiontype type;

    public StateCensusAnalyserException(Exceptiontype type, String message)
    {
        super(message);
        this.type = type;
    }
}