package com.mmtou.poetry.common;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import com.alibaba.fastjson.JSON;

import org.apache.commons.codec.digest.DigestUtils;

public class Token {

  private long userId;

  private String userName;

  private long createTime;

  private Token(long userId, String userName) {
    this.userId = userId;
    this.userName = userName;
    this.createTime = System.currentTimeMillis();
  }

  public static String getToken(long userId, String userName) {
    String source = JSON.toJSONString(new Token(userId, userName));

    String sha256 = DigestUtils.sha256Hex(source);

    return Joiner.on("").join(Lists.newArrayList("U", userId, "U", sha256)).toUpperCase();
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

}
