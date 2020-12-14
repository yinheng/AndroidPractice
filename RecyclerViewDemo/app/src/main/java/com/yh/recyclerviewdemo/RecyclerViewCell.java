package com.yh.recyclerviewdemo;

public class RecyclerViewCell {
    private String imgId;
    private String name;
    private String dec;

    public RecyclerViewCell(String imgId, String name, String dec) {
        this.imgId = imgId;
        this.name = name;
        this.dec = dec;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
