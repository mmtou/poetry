package com.mmtou.poetry.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Page implements Serializable {

  private int pageNo = 1;
  private int pageSize = 20;
  private int totalRecord;
  private int totalPage;

  public Page() {
  }

  public Page(Integer pageNo, Integer pageSize) {
    if (pageNo != null) {
      this.pageNo = pageNo;
    }

    if (pageSize != null) {
      this.pageSize = pageSize;
    }
  }

  public void setTotalRecord(int totalRecord) {
    this.totalRecord = totalRecord;
    this.totalPage = totalRecord % this.pageSize == 0 ? totalRecord / this.pageSize
        : totalRecord / this.pageSize + 1;
  }

  public int getPageNo() {
    return this.pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPage() {
    return this.totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getTotalRecord() {
    return this.totalRecord;
  }

  @JsonIgnore
  public long getOffset() {
    return (this.pageNo - 1) * this.pageSize;
  }

  @JsonIgnore
  public int getLimit() {
    return this.pageSize;
  }

}
