package com.neuedu.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 18:29
 *
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//只包含非空值，空值不包括
public class ServerResponse<T> {
    /**
     * 返回前端状态码 表示是否成功
     */
    private int status;
    /**
     * 返回前端的的数据 泛型  一般为成功的值
     */
    private T data;
    /**
     * 返回前端的字符串  一般为错误信息
     */
    private String msg;

    private ServerResponse() {

    }

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    /*
     * 接口调用成功时回调
     * */
    public static ServerResponse serverResponseSuccess() {
        return new ServerResponse(ResponseCode.SUCCESS);
    }

    public static ServerResponse serverResponseSuccess(int status) {
        return new ServerResponse<>(status);
    }

    public static <T> ServerResponse serverResponseSuccess(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS, data);
    }

    /**
     * 想要让data传入字符串 调用这个方法
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ServerResponse serverResponseSuccess(T data, String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS, data, msg);
    }

    /*
    接口调用失败时回调
    * */
    public static ServerResponse serverResponseError() {
        return new ServerResponse(ResponseCode.ERROR);
    }

    public static ServerResponse serverResponseError(int status) {
        return new ServerResponse(status);
    }

    public static ServerResponse serverResponseError(String msg) {
        return new ServerResponse(ResponseCode.ERROR, msg);
    }

    public static ServerResponse serverResponseError(int status, String msg) {
        return new ServerResponse(status, msg);
    }


    /**
     * 判断接口的返回值是否正确
     */
    @JsonIgnore   //忽略
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS;
    }
}
