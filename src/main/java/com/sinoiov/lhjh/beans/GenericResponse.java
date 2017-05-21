package com.sinoiov.lhjh.beans;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 通用Bean
 * Created by lidawei on 2017/5/21.
 */
public class GenericResponse<T> implements Serializable {
    public transient static final int SUCCEEDED = 0;
    public transient static final int FAILED = 1;
    private static final long serialVersionUID = -1889283080333314159L;

    private int code;
    private String message;
    private T data;

    public GenericResponse() {
    }

    public GenericResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public GenericResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static int getSUCCEEDED() {
        return SUCCEEDED;
    }

    public static int getFAILED() {
        return FAILED;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
