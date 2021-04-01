package com.FortumoTask.httpModels;

public enum HttpStatusError {

    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request");

    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusError(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
