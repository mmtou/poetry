package com.mmtou.poetry.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CommentInfo implements Serializable {

  private Long id;

  private Long userId;

  private String userName;

  private Long subjectId;

  private Byte subjectType;

  private Long parentId;

  private String content;

  private List<CommentAtInfo> commentAts;

  private Long createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public List<CommentAtInfo> getCommentAts() {
    return commentAts;
  }

  public void setCommentAts(List<CommentAtInfo> commentAts) {
    this.commentAts = commentAts;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    if (createTime != null) {
      this.createTime = createTime.getTime();
    }
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
}
