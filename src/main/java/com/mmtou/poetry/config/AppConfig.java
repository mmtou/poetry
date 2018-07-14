package com.mmtou.poetry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("appConfig")
@ConfigurationProperties("app")
public class AppConfig {

  private String baseDir;

  private String baseDownloadUrl;

  public String getBaseDir() {
    return baseDir;
  }

  public void setBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }

  public String getBaseDownloadUrl() {
    return baseDownloadUrl;
  }

  public void setBaseDownloadUrl(String baseDownloadUrl) {
    this.baseDownloadUrl = baseDownloadUrl;
  }
}
