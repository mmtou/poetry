package com.mmtou.poetry.pojo;

import java.io.Serializable;

/**
 * 附加信息
 */
public class AdditionInfo implements Serializable {

  private long commentCount;

  private long likeCount;

  public long getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(long commentCount) {
    this.commentCount = commentCount;
  }

  public long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(long likeCount) {
    this.likeCount = likeCount;
  }
}
