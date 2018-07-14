package com.mmtou.poetry.web;

import com.google.common.collect.Lists;

import com.mmtou.poetry.common.Request;
import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.pojo.BannerInfo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mmtou.poetry.common.Response.success;

@RestController
@RequestMapping("/api/banner/")
public class BannerController {

  @GetMapping("list")
  public Response<List<BannerInfo>> list(@ModelAttribute Request request) {
    BannerInfo info = new BannerInfo();
    info.setImageUrl(
        "https://muse-ui.org/static/img/carousel3.e10327f.jpg");
    BannerInfo info2 = new BannerInfo();
    info2.setImageUrl(
        "https://muse-ui.org/static/img/carousel3.e10327f.jpg");
    List<BannerInfo> list = Lists.newArrayList(info, info2);
    return success(list);
  }

}
