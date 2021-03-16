package com.yh.todaynews.repositories.remote.model;

import com.google.gson.annotations.SerializedName;

public class NewsData {
    private String reason;
    private Result result;
    @SerializedName("error_code")
    private Integer errorCode;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
