package com.mmtou.poetry.request;

import com.mmtou.poetry.common.Request;

public class CommentListRequest extends Request {

  private Long subjectId;

  private Byte subjectType;

  private Long userId;

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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
