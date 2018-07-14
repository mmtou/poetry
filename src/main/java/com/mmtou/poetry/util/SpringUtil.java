package com.mmtou.poetry.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public SpringUtil() {
  }

  public void setApplicationContext(ApplicationContext application) throws BeansException {
    applicationContext = application;
  }

  public static <T> T getObject(String name, Class<T> requiredType) {
    return applicationContext.getBean(name, requiredType);
  }
}
