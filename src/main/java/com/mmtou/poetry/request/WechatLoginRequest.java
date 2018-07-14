package com.mmtou.poetry.request;

import java.io.Serializable;

public class WechatLoginRequest implements Serializable {

  private String jsCode;

  private String userName;

  public String getJsCode() {
    return jsCode;
  }

  public void setJsCode(String jsCode) {
    this.jsCode = jsCode;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
