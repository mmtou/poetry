package com.mmtou.poetry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("baiduConfig")
@ConfigurationProperties("app.baidu")
public class BaiduConfig {

  private String appKey;

  private String secretKey;

  private int per;

  private int spd;

  private int pit;

  private int vol;

  public String url;

  private String cuid;

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public int getPer() {
    return per;
  }

  public void setPer(int per) {
    this.per = per;
  }

  public int getSpd() {
    return spd;
  }

  public void setSpd(int spd) {
    this.spd = spd;
  }

  public int getPit() {
    return pit;
  }

  public void setPit(int pit) {
    this.pit = pit;
  }

  public int getVol() {
    return vol;
  }

  public void setVol(int vol) {
    this.vol = vol;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCuid() {
    return cuid;
  }

  public void setCuid(String cuid) {
    this.cuid = cuid;
  }
}
