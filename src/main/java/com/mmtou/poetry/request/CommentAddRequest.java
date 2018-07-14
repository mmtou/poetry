package com.mmtou.poetry.request;

import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.pojo.CommentAtInfo;

import java.util.List;

public class CommentAddRequest extends Request {

  private Long subjectId;

  // 主体类型: 1poetry; 2author
  private Byte subjectType;

  private Long parentId;

  private String content;

  private List<CommentAtInfo> commentAts;

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
}
