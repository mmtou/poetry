package com.mmtou.poetry.entity;

import java.io.Serializable;

/**
 * poetry
 * @author 
 */
public class PoetryWithBLOBs extends Poetry implements Serializable {
    private String content;

    private String yunlvRule;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYunlvRule() {
        return yunlvRule;
    }

    public void setYunlvRule(String yunlvRule) {
        this.yunlvRule = yunlvRule;
    }
}