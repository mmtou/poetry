package com.mmtou.poetry.pojo;

import java.io.Serializable;

public class BannerInfo implements Serializable {

  private String imageUrl;

  private String link;

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
