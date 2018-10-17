package com.mmtou.poetry.service;

import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.config.WechatConfig;
import com.mmtou.poetry.entity.User;
import com.mmtou.poetry.entity.UserExtend;
import com.mmtou.poetry.entity.UserExtendExample;
import com.mmtou.poetry.mapper.UserDAO;
import com.mmtou.poetry.mapper.UserExtendDAO;
import com.mmtou.poetry.pojo.PoetryAuthorInfo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mmtou.poetry.common.Response.success;

@Service
public class UserService {

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private UserExtendDAO userExtendDAO;

  @Autowired
  private WechatConfig wechatConfig;

  /**
   * 查询user详情
   */
  public Response<PoetryAuthorInfo> get(Request<Long> request) {
    User poetryAuthor = userDAO.selectByPrimaryKey(request.getRequest());
    if (poetryAuthor == null) {
      return success();
    }
    PoetryAuthorInfo info = new PoetryAuthorInfo();
    BeanUtils.copyProperties(poetryAuthor, info);

    return success(info);
  }

  /**
   * 查询user详情
   */
  public Response<PoetryAuthorInfo> detail(Request<Long> request) {
    User poetryAuthor = userDAO.selectByPrimaryKey(request.getRequest());
    if (poetryAuthor == null) {
      return success();
    }
    PoetryAuthorInfo info = new PoetryAuthorInfo();
    BeanUtils.copyProperties(poetryAuthor, info);

    UserExtendExample example = new UserExtendExample();
    example.createCriteria().andUserIdEqualTo(poetryAuthor.getId()).andDeleteFlagEqualTo((byte) 0);
    List<UserExtend> userExtends = userExtendDAO.selectByExampleWithBLOBs(example);
    if (userExtends != null && !userExtends.isEmpty()) {
      info.setDescription(userExtends.get(0).getDescription());
    }

    return success(info);
  }

//  /**
//   * 微信登录
//   */
//  public Response<LoginResultInfo> wechatLogin(WechatLoginRequest request) {
//    String jsCode = request.getJsCode();
//    String userName = request.getUserName();
//
//    if (StringUtils.isBlank(jsCode) || StringUtils.isBlank(userName)) {
//      return fail("400", "参数错误");
//    }
//
//    Map<String, Object> params = Maps.newHashMap();
//    params.put("appid", wechatConfig.getAppid());
//    params.put("secret", wechatConfig.getSecret());
//    params.put("js_code", jsCode);
//
//    RestTemplate restTemplate = new RestTemplate();
//    String response = restTemplate
//        .getForObject(wechatConfig.getJscode2session(), String.class, params);
//    WechatLoginResult wechatLoginResult = JSON.parseObject(response, WechatLoginResult.class);
//
//    String openid = wechatLoginResult.getOpenid();
//    if (StringUtils.isBlank(openid)) {
//      return fail("500", "登录失败，请重新登录");
//    }
//
//    UserExample userExample = new UserExample();
//    userExample.createCriteria().andOpenidEqualTo(openid).andDeleteFlagEqualTo((byte) 0);
//    List<User> users = userDAO.selectByExample(userExample);
//
//    // 没有openid，则创建用户
//    User user = null;
//    if (users == null || users.isEmpty()) {
//      user = new User();
//      user.setUserName(userName);
//      user.setOpenid(openid);
//      user.setCreateTime(new Date());
//      userDAO.insertSelective(user);
//    } else {
//      user = users.get(0);
//    }
//
//    String token = Token.getToken(user.getId(), user.getUserName());
//
//    LoginResultInfo loginResultInfo = new LoginResultInfo();
//    loginResultInfo.setUserName(user.getUserName());
//    loginResultInfo.setToken(token);
//    return success(loginResultInfo);
//  }

}
