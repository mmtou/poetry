package com.mmtou.poetry.mapper;

import com.mmtou.poetry.entity.Poetry;
import com.mmtou.poetry.entity.PoetryExample;
import org.springframework.stereotype.Repository;

/**
 * PoetryDAO继承基类
 */
@Repository
public interface PoetryDAO extends MyBatisBaseDao<Poetry, Long, PoetryExample> {
}