package com.mmtou.poetry.mapper;

import com.mmtou.poetry.entity.Comment;
import com.mmtou.poetry.entity.CommentExample;
import org.springframework.stereotype.Repository;

/**
 * CommentDAO继承基类
 */
@Repository
public interface CommentDAO extends MyBatisBaseDao<Comment, Long, CommentExample> {
}