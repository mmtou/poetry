package com.mmtou.poetry.pojo;

public class PoetryAuthorInfo {

  private Long id;

  private String name;

  private String introL;

  private String introS;

  private Byte dynasty;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntroL() {
    return introL;
  }

  public void setIntroL(String introL) {
    this.introL = introL;
  }

  public String getIntroS() {
    return introS;
  }

  public void setIntroS(String introS) {
    this.introS = introS;
  }

  public Byte getDynasty() {
    return dynasty;
  }

  public void setDynasty(Byte dynasty) {
    this.dynasty = dynasty;
  }
}
