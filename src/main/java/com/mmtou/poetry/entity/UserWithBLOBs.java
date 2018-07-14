package com.mmtou.poetry.entity;

import java.io.Serializable;

/**
 * user
 * @author 
 */
public class UserWithBLOBs extends User implements Serializable {
    private String introL;

    private String introS;

    private static final long serialVersionUID = 1L;

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
}