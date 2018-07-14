package com.mmtou.poetry.web;

import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.pojo.PoetryInfo;
import com.mmtou.poetry.request.PoetryListRequest;
import com.mmtou.poetry.service.PoetryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/poetry/")
public class PoetryController {

  @Autowired
  private PoetryService poetryService;

  @GetMapping("list")
  public Response<List<PoetryInfo>> list(@ModelAttribute PoetryListRequest request) {
    return poetryService.list(request);
  }

  @GetMapping("{id}")
  public Response<PoetryInfo> list(@PathVariable("id") long id) {
    return poetryService.get(new Request<Long>(id));
  }

}
