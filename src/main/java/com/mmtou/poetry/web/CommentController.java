package com.mmtou.poetry.web;

import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.pojo.CommentInfo;
import com.mmtou.poetry.request.CommentAddRequest;
import com.mmtou.poetry.request.CommentListRequest;
import com.mmtou.poetry.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comment/")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("add")
  public Response<Long> add(@RequestBody CommentAddRequest request) {
    return commentService.add(request);
  }

  @GetMapping("list")
  public Response<List<CommentInfo>> list(@ModelAttribute CommentListRequest request) {
    return commentService.list(request);
  }

}
