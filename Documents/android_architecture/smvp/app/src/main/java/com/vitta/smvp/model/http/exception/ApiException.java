package com.vitta.smvp.model.http.exception;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 16:45
 * 描述：ApiException
 */

public class ApiException extends Exception {

    private int code;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
