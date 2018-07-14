package com.mmtou.poetry.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * comment
 * @author 
 */
public class Comment implements Serializable {
    private Long id;

    private Long userId;

    private String userName;

    /**
     * 评论的主体id，比如Poetry、author
     */
    private Long subjectId;

    /**
     * 主体类型: 1poetry; 2author
     */
    private Byte subjectType;

    /**
     * 该记录的父记录id
     */
    private Long parentId;

    private String content;

    private Date createTime;

    /**
     * 0未删除，1已删除
     */
    private Byte deleteFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Byte getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Byte subjectType) {
        this.subjectType = subjectType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}