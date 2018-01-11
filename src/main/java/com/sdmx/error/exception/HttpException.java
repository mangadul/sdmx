package com.sdmx.error.exception;

public class HttpException extends RuntimeException {

    private int statusCode = 404;

    private String message;

    public HttpException() {}

    public HttpException(int statusCode) {
        this.statusCode = statusCode;
        message = "error.http."+ statusCode;
    }

    public HttpException(int statusCode, String message) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
