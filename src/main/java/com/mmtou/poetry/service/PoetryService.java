package com.mmtou.poetry.service;

import com.google.common.collect.Lists;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import com.mmtou.poetry.common.Page;
import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.config.EventBusConfig;
import com.mmtou.poetry.entity.Poetry;
import com.mmtou.poetry.entity.PoetryExample;
import com.mmtou.poetry.mapper.PoetryDAO;
import com.mmtou.poetry.mapper.PoetryExtDAO;
import com.mmtou.poetry.pojo.PoetryInfo;
import com.mmtou.poetry.request.PoetryListRequest;
import com.mmtou.poetry.util.SpeechUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;

import static com.mmtou.poetry.common.Response.success;

@Service
public class PoetryService {

  @Autowired
  private PoetryDAO poetryDAO;

  @Autowired
  private PoetryExtDAO poetryExtDAO;

  /**
   * 根据条件查询poetry list
   */
  public Response<List<PoetryInfo>> list(PoetryListRequest request) {
    String authorName = request.getAuthorName();
    String keyword = request.getKeyword();
    Byte orderField = request.getOrderField();
    Byte orderBy = request.getOrderBy();
    Page page = request.getPage();

    PoetryExample poetryExample = new PoetryExample();
    PoetryExample.Criteria criteria = poetryExample.createCriteria();
    if (StringUtils.isNotBlank(authorName)) {
      criteria.andAuthorNameEqualTo(authorName);
    }
    if (StringUtils.isNotBlank(keyword)) {
      criteria.andTitleEqualTo(keyword);
    }
    String orderFieldStr = "id";
    if (orderField != null && orderField == 1) {
      orderFieldStr = "hot_top";
    }
    String orderByStr = "desc";
    if (orderBy != null && orderBy == 1) {
      orderByStr = "asc";
    }
    poetryExample.setOrderByClause(orderFieldStr + " " + orderByStr);
    poetryExample.setOffset(page.getOffset());
    poetryExample.setLimit(page.getLimit());
    List<Poetry> poetries = poetryDAO.selectByExampleWithBLOBs(poetryExample);
    if (poetries == null && poetries.isEmpty()) {
      return success();
    }

    List<PoetryInfo> list = Lists.newArrayList();
    for (Poetry poetry : poetries) {
      PoetryInfo info = new PoetryInfo();
      BeanUtils.copyProperties(poetry, info);
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

    info.setAudioUrl(SpeechUtil.generateSpeech(info.getContent()));

    EventBusConfig.Event<Long> event = new EventBusConfig.Event(
        EventBusConfig.EventType.READ, id);
    asyncEventBus.post(event);

    return success(info);
  }

  @Autowired
  private AsyncEventBus asyncEventBus;

  //注册这个监听器
  @PostConstruct
  public void register() {
    asyncEventBus.register(this);
  }

  @Subscribe
  public void updateHotTop(EventBusConfig.Event<Long> event) {
    long hotTop = 0;
    long readCount = 0;
    long commentCount = 0;
    long likeCount = 0;

    EventBusConfig.EventType eventType = event.getEventType();
    switch (eventType) {
      case READ:
        hotTop = 1;
        readCount += 1;
        break;
      case COMMENT:
        hotTop = 10;
        commentCount += 1;
        break;
      case LIKE:
        hotTop = 5;
        likeCount += 1;
        break;
    }

    Poetry poetry = new Poetry();
    poetry.setId(event.getContent());
    poetry.setHotTop(hotTop);
    poetry.setCommentCount(commentCount);
    poetry.setLikeCount(likeCount);
    poetryExtDAO.updateByExampleSelectiveExt(poetry);
  }

}
