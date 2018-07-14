package com.mmtou.poetry.mapper;

import com.mmtou.poetry.entity.User;
import com.mmtou.poetry.entity.UserExample;
import org.springframework.stereotype.Repository;

/**
 * UserDAO继承基类
 */
@Repository
public interface UserDAO extends MyBatisBaseDao<User, Long, UserExample> {
}