package com.mmtou.poetry;

import com.google.common.collect.Lists;

import com.mmtou.poetry.filter.AuthenticateFilter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@MapperScan("com.mmtou.poetry.mapper")
@EnableScheduling
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  @Bean
  public FilterRegistrationBean authenticateFilterBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setName("authenticateFilter");
    AuthenticateFilter authenticateFilter = new AuthenticateFilter();
    registrationBean.setFilter(authenticateFilter);
    registrationBean.setOrder(1);
    List<String> urlList = Lists.newArrayList();
    urlList.add("/api/*");
    registrationBean.setUrlPatterns(urlList);
    return registrationBean;
  }

}
