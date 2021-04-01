package com.FortumoTask.httpModels;

public class HttpParsingExeption extends Exception {

    private final HttpStatusError errorCode;

    public HttpParsingExeption(HttpStatusError errorCode) {
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public HttpStatusError getErrorCode() {
        return errorCode;
    }
}
