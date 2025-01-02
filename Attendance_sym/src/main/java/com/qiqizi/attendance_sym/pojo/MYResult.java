package com.qiqizi.attendance_sym.pojo;

public class MYResult {
    private String code;
    private String message;
    private String data;

    public MYResult(String code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public MYResult() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MYResult success() {
        return new MYResult("1", "success", null);
    }

    public MYResult fail() {
        return new MYResult("2", "fail", null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
