package com.mmtou.poetry.request;

import com.mmtou.poetry.common.Request;

public class PoetryListRequest extends Request {

  private String authorName;

  private String keyword;

  /**
   * 0热度; 1创建时间;
   */
  private Byte orderField;

  private Byte orderBy;

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public Byte getOrderField() {
    return orderField;
  }

  public void setOrderField(Byte orderField) {
    this.orderField = orderField;
  }

  public Byte getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Byte orderBy) {
    this.orderBy = orderBy;
  }
}
