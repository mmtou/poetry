package com.mmtou.poetry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.wechat")
public class WechatConfig {

  private String appid;

  private String secret;

  private String jscode2session;

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getJscode2session() {
    return jscode2session;
  }

  public void setJscode2session(String jscode2session) {
    this.jscode2session = jscode2session;
  }
}
