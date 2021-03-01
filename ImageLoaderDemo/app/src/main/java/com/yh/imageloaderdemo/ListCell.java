package com.yh.imageloaderdemo;

public class ListCell {
  private String name;
  private String imageUrl;
  private String comment;

  public ListCell(String name, String imageUrl, String comment) {
    this.name = name;
    this.imageUrl = imageUrl;
    this.comment = comment;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
