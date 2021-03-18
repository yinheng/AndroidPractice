package com.yh.todaynews.data.repositories.remote.model;

import com.google.gson.annotations.SerializedName;
import com.yh.todaynews.data.model.NewsData;

import java.util.List;

public class Result {
    private String stat;
    private List<NewsData> data;
    private Integer page;
    @SerializedName("pageSize")
    private Integer pageSize;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<NewsData> getData() {
        return data;
    }

    public void setData(List<NewsData> data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
