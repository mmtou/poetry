//package com.mmtou.poetry.web;
//
//import com.mmtou.poetry.common.Response;
//import com.mmtou.poetry.pojo.LoginResultInfo;
//import com.mmtou.poetry.request.WechatLoginRequest;
//import com.mmtou.poetry.service.UserService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/authorize/")
//public class AuthorizeController {
//
//  private Logger log = LoggerFactory.getLogger(AuthorizeController.class);
//
//  @Autowired
//  private UserService userService;
//
//  @PostMapping("wechatLogin")
//  public Response<LoginResultInfo> wechatLogin(@RequestBody WechatLoginRequest request) {
//    return userService.wechatLogin(request);
//  }
//
//}
