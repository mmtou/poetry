package com.mmtou.poetry.web;

import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.pojo.PoetryAuthorInfo;
import com.mmtou.poetry.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {

  @Autowired
  private UserService authorService;

  @GetMapping("{id}")
  public Response<PoetryAuthorInfo> list(@PathVariable("id") long id) {
    return authorService.get(new Request<Long>(id));
  }

}
