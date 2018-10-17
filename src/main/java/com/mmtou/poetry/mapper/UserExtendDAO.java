package com.mmtou.poetry.mapper;

import com.mmtou.poetry.entity.UserExtend;
import com.mmtou.poetry.entity.UserExtendExample;
import org.springframework.stereotype.Repository;

/**
 * UserExtendDAO继承基类
 */
@Repository
public interface UserExtendDAO extends MyBatisBaseDao<UserExtend, Long, UserExtendExample> {
}