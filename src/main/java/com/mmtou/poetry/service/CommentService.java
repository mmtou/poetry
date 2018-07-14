package com.mmtou.poetry.service;

import com.google.common.collect.Lists;
import com.google.common.eventbus.AsyncEventBus;

import com.mmtou.poetry.common.Page;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.config.EventBusConfig;
import com.mmtou.poetry.entity.Comment;
import com.mmtou.poetry.entity.CommentAt;
import com.mmtou.poetry.entity.CommentAtExample;
import com.mmtou.poetry.entity.CommentExample;
import com.mmtou.poetry.mapper.CommentAtDAO;
import com.mmtou.poetry.mapper.CommentDAO;
import com.mmtou.poetry.pojo.CommentAtInfo;
import com.mmtou.poetry.pojo.CommentInfo;
import com.mmtou.poetry.request.CommentAddRequest;
import com.mmtou.poetry.request.CommentListRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.mmtou.poetry.common.Response.fail;
import static com.mmtou.poetry.common.Response.success;

@Service
public class CommentService {

  @Autowired
  private CommentDAO commentDAO;

  @Autowired
  private CommentAtDAO commentAtDAO;

  @Autowired
  private AsyncEventBus asyncEventBus;

  /**
   * 添加评论
   */
  public Response<Long> add(CommentAddRequest request) {
    Long subjectId = request.getSubjectId();
    Byte subjectType = request.getSubjectType();
    Long parentId = request.getParentId();
    String content = request.getContent();
    List<CommentAtInfo> commentAts = request.getCommentAts();

    // 参数校验
    if (subjectId == null || subjectType == null || StringUtils.isBlank(content)) {
      return fail("400", "参数错误");
    }

    Long currentUserId = request.getCurrentUserId();
    Date currentTime = new Date();

    // add comment
    Comment comment = new Comment();
    comment.setUserId(currentUserId);
    comment.setUserName(request.getCurrentUserName());
    comment.setSubjectId(subjectId);
    comment.setSubjectType(subjectType);
    comment.setParentId(parentId);
    comment.setContent(content);
    comment.setCreateTime(currentTime);
    commentDAO.insert(comment);

    Long id = comment.getId();

    // add comment @
    if (commentAts != null && !commentAts.isEmpty()) {
      for (CommentAtInfo commentAtInfo : commentAts) {
        CommentAt commentAt = new CommentAt();
        commentAt.setCommentId(id);
        commentAt.setUserId(commentAtInfo.getUserId());
        commentAt.setUserName(commentAtInfo.getUserName());
        commentAt.setCreateTime(currentTime);
        commentAt.setCreateBy(currentUserId);
        commentAtDAO.insert(commentAt);
      }
    }

    // 通知
    EventBusConfig.Event<Long> event = new EventBusConfig.Event(
        EventBusConfig.EventType.COMMENT, subjectId);
    asyncEventBus.post(event);

    return success(id);
  }

  /**
   * 查询评论列表
   */
  public Response<List<CommentInfo>> list(CommentListRequest request) {
    Long subjectId = request.getSubjectId();
    Byte subjectType = request.getSubjectType();
    Long userId = request.getUserId();
    Page page = request.getPage();

    if (subjectId == null || subjectType == null) {
      return fail("400", "参数错误");
    }

    CommentExample commentExample = new CommentExample();
    CommentExample.Criteria criteria = commentExample.createCriteria();
    criteria.andSubjectIdEqualTo(subjectId);
    criteria.andSubjectTypeEqualTo(subjectType);
    if (userId != null) {
      criteria.andUserIdEqualTo(userId);
    }

    commentExample.setOffset(page.getOffset());
    commentExample.setLimit(page.getLimit());
    List<Comment> comments = commentDAO.selectByExample(commentExample);

    if (comments == null || comments.isEmpty()) {
      return success();
    }

    List<CommentInfo> list = Lists.newArrayList();
    for (Comment comment : comments) {
      CommentInfo commentInfo = new CommentInfo();
      BeanUtils.copyProperties(comment, commentInfo);

      CommentAtExample commentAtExample = new CommentAtExample();
      CommentAtExample.Criteria commentAtExampleCriteria = commentAtExample.createCriteria();
      commentAtExampleCriteria.andCommentIdEqualTo(comment.getId());
      List<CommentAt> commentAts = commentAtDAO.selectByExample(commentAtExample);
      if (commentAts != null && !commentAts.isEmpty()) {
        List<CommentAtInfo> commentAtInfos = Lists.newArrayList();
        for (CommentAt commentAt : commentAts) {
          CommentAtInfo commentAtInfo = new CommentAtInfo();
          BeanUtils.copyProperties(commentAt, commentAtInfo);
          commentAtInfos.add(commentAtInfo);
        }
        commentInfo.setCommentAts(commentAtInfos);
      }
      list.add(commentInfo);
    }

    return success(list);
  }

}
