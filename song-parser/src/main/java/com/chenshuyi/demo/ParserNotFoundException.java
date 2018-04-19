package com.chenshuyi.demo;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class ParserNotFoundException extends RuntimeException{
    private String code;

    private String message;

    public ParserNotFoundException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
