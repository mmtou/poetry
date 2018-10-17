package com.mmtou.poetry.service;

import com.google.common.collect.Lists;

import com.mmtou.poetry.common.Page;
import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.entity.Poetry;
import com.mmtou.poetry.entity.PoetryExample;
import com.mmtou.poetry.mapper.PoetryDAO;
import com.mmtou.poetry.mapper.PoetryExtDAO;
import com.mmtou.poetry.pojo.PoetryAuthorInfo;
import com.mmtou.poetry.pojo.PoetryInfo;
import com.mmtou.poetry.request.PoetryListRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mmtou.poetry.common.Response.success;

@Service
public class PoetryService {

  @Autowired
  private PoetryDAO poetryDAO;

  @Autowired
  private PoetryExtDAO poetryExtDAO;

  @Autowired
  private UserService userService;

  /**
   * 根据条件查询poetry list
   */
  public Response<List<PoetryInfo>> list(PoetryListRequest request) {
    Long authorId = request.getAuthorId();
    String authorName = request.getAuthorName();
    String keyword = request.getKeyword();
    Byte orderField = request.getOrderField();
    Byte orderBy = request.getOrderBy();
    Page page = request.getPage();

    PoetryExample poetryExample = new PoetryExample();
    PoetryExample.Criteria criteria = poetryExample.createCriteria();
    if (authorId != null) {
      criteria.andAuthorIdEqualTo(authorId);
    }
    if (StringUtils.isNotBlank(authorName)) {
      criteria.andAuthorNameEqualTo(authorName);
    }
    if (StringUtils.isNotBlank(keyword)) {
      criteria.andTitleEqualTo(keyword);
    }
    String orderFieldStr = "id";
    if (orderField != null && orderField == 1) {
      orderFieldStr = "score";
    }
    String orderByStr = "desc";
    if (orderBy != null && orderBy == 1) {
      orderByStr = "asc";
    }
    poetryExample.setOrderByClause(orderFieldStr + " " + orderByStr);
    poetryExample.setOffset(page.getOffset());
    poetryExample.setLimit(page.getLimit());
    List<Poetry> poem = poetryDAO.selectByExampleWithBLOBs(poetryExample);
    if (poem == null && poem.isEmpty()) {
      return success();
    }

    List<PoetryInfo> list = Lists.newArrayList();
    for (Poetry poetry : poem) {
      PoetryInfo info = new PoetryInfo();
      BeanUtils.copyProperties(poetry, info);

      Request<Long> getUserRequest = new Request<Long>();
      getUserRequest.setRequest(poetry.getAuthorId());
      PoetryAuthorInfo poetryAuthorInfo = userService.get(getUserRequest).getResult();
      if (poetryAuthorInfo != null) {
        info.setAvatar(poetryAuthorInfo.getAvatar());
      }

      list.add(info);
    }

    return success(list);
  }

  /**
   * 根据id查询poetry
   */
  public Response<PoetryInfo> get(Request<Long> request) {
    Long id = request.getRequest();
    Poetry poetry = poetryDAO.selectByPrimaryKey(id);
    PoetryInfo info = new PoetryInfo();
    BeanUtils.copyProperties(poetry, info);

    return success(info);
  }

}
