package com.bridgelabz.exception;

public class StateCensusAnalyserException extends Exception {
    public enum Exceptiontype {
        FILE_NOT_FOUND;
    }
   public Exceptiontype type;

    public StateCensusAnalyserException(Exceptiontype type, String message) {
        super(message);
        this.type = type;
    }
}