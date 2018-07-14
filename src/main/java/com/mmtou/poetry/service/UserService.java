package com.mmtou.poetry.service;
/**
 * user avatar的规则，host/upload/avatar/userId.png
 * 例: https://poetry.mmtou.xyz/upload/avatar/1.png
 */

import com.google.common.collect.Maps;

import com.alibaba.fastjson.JSON;
import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.common.Token;
import com.mmtou.poetry.config.WechatConfig;
import com.mmtou.poetry.entity.User;
import com.mmtou.poetry.entity.UserExample;
import com.mmtou.poetry.mapper.UserDAO;
import com.mmtou.poetry.pojo.LoginResultInfo;
import com.mmtou.poetry.pojo.PoetryAuthorInfo;
import com.mmtou.poetry.pojo.WechatLoginResult;
import com.mmtou.poetry.request.WechatLoginRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mmtou.poetry.common.Response.fail;
import static com.mmtou.poetry.common.Response.success;

@Service
public class UserService {

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private WechatConfig wechatConfig;

  /**
   * 查询user详情
   */
  public Response<PoetryAuthorInfo> get(Request<Long> request) {
    User poetryAuthor = userDAO.selectByPrimaryKey(request.getRequest());
    PoetryAuthorInfo info = new PoetryAuthorInfo();
    BeanUtils.copyProperties(poetryAuthor, info);
    return success(info);
  }

  /**
   * 微信登录
   */
  public Response<LoginResultInfo> wechatLogin(WechatLoginRequest request) {
    String jsCode = request.getJsCode();
    String userName = request.getUserName();

    if (StringUtils.isBlank(jsCode) || StringUtils.isBlank(userName)) {
      return fail("400", "参数错误");
    }

    Map<String, Object> params = Maps.newHashMap();
    params.put("appid", wechatConfig.getAppid());
    params.put("secret", wechatConfig.getSecret());
    params.put("js_code", jsCode);

    RestTemplate restTemplate = new RestTemplate();
    String response = restTemplate
        .getForObject(wechatConfig.getJscode2session(), String.class, params);
    WechatLoginResult wechatLoginResult = JSON.parseObject(response, WechatLoginResult.class);

    String openid = wechatLoginResult.getOpenid();
    if (StringUtils.isBlank(openid)) {
      return fail("500", "登录失败，请重新登录");
    }

    UserExample userExample = new UserExample();
    userExample.createCriteria().andOpenidEqualTo(openid).andDeleteFlagEqualTo((byte) 0);
    List<User> users = userDAO.selectByExample(userExample);

    // 没有openid，则创建用户
    User user = null;
    if (users == null || users.isEmpty()) {
      user = new User();
      user.setUserName(userName);
      user.setOpenid(openid);
      user.setCreateTime(new Date());
      userDAO.insertSelective(user);
    } else {
      user = users.get(0);
    }

    String token = Token.getToken(user.getId(), user.getUserName());

    LoginResultInfo loginResultInfo = new LoginResultInfo();
    loginResultInfo.setUserName(user.getUserName());
    loginResultInfo.setToken(token);
    return success(loginResultInfo);
  }

}
