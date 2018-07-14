package com.mmtou.poetry.mapper;

import com.mmtou.poetry.entity.CommentAt;
import com.mmtou.poetry.entity.CommentAtExample;
import org.springframework.stereotype.Repository;

/**
 * CommentAtDAO继承基类
 */
@Repository
public interface CommentAtDAO extends MyBatisBaseDao<CommentAt, Long, CommentAtExample> {
}