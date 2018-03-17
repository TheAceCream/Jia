package com.jia.jia.module;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class Response<T> {

    private int code;
    private String msg;
    private T data;

    public Response(){

    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
