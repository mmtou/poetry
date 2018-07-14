package com.mmtou.poetry.filter;

import com.alibaba.fastjson.JSON;
import com.mmtou.poetry.pojo.UserInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AuthenticateFilter implements Filter {

  private Logger log = LoggerFactory.getLogger(AuthenticateFilter.class);

  @Autowired
  private StringRedisTemplate redisTemplate;

  private static final String CACHE_TOKEN_PREFIX = "TOKEN:%s";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;

//    String token = request.getHeader("token");
//    if (StringUtils.isBlank(token)) {
//      fail(servletResponse);
//      return;
//    }
//
//    String userInfoStr = redisTemplate.opsForValue().get(String.format(CACHE_TOKEN_PREFIX, token));
//    if (StringUtils.isBlank(userInfoStr)) {
//      fail(servletResponse);
//      return;
//    }
//    redisTemplate.opsForValue().set(String.format(CACHE_TOKEN_PREFIX, token), userInfoStr);

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {

  }

  private void fail(ServletResponse servletResponse) {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    try {
      response.sendError(HttpStatus.UNAUTHORIZED.value());
    } catch (IOException e) {
      log.error("fail error", e);
    }
  }
}
