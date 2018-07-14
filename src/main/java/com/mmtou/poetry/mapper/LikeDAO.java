package com.mmtou.poetry.mapper;

import com.mmtou.poetry.entity.Like;
import com.mmtou.poetry.entity.LikeExample;
import org.springframework.stereotype.Repository;

/**
 * LikeDAO继承基类
 */
@Repository
public interface LikeDAO extends MyBatisBaseDao<Like, Long, LikeExample> {
}