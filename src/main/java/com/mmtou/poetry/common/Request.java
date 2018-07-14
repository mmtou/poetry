package com.mmtou.poetry.common;

import java.io.Serializable;

public class Request<T> implements Serializable {

  private T request;

  private Page page;

  private Long currentUserId;

  private String currentUserName;

  public Request(T request) {
    this.request = request;
  }

  public Request() {
  }

  public T getRequest() {
    return request;
  }

  public void setRequest(T request) {
    this.request = request;
  }

  public Page getPage() {
    if (page == null) {
      page = new Page();
    }
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public Long getCurrentUserId() {
    return currentUserId;
  }

  public void setCurrentUserId(Long currentUserId) {
    this.currentUserId = currentUserId;
  }

  public String getCurrentUserName() {
    return currentUserName;
  }

  public void setCurrentUserName(String currentUserName) {
    this.currentUserName = currentUserName;
  }
}
