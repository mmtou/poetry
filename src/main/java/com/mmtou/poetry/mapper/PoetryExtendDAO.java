package com.mmtou.poetry.mapper;

import com.mmtou.poetry.entity.PoetryExtend;
import com.mmtou.poetry.entity.PoetryExtendExample;
import org.springframework.stereotype.Repository;

/**
 * PoetryExtendDAO继承基类
 */
@Repository
public interface PoetryExtendDAO extends MyBatisBaseDao<PoetryExtend, Long, PoetryExtendExample> {
}