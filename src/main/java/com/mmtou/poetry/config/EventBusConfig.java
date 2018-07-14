package com.mmtou.poetry.config;

import com.google.common.eventbus.AsyncEventBus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class EventBusConfig {

  @Bean
  public AsyncEventBus eventBus() {
    return new AsyncEventBus(Executors.newFixedThreadPool(100));
  }

  public static class Event<T> {

    private EventType eventType;

    private T content;

    public Event(EventType eventType, T content) {
      this.eventType = eventType;
      this.content = content;
    }

    public EventType getEventType() {
      return eventType;
    }

    public void setEventType(EventType eventType) {
      this.eventType = eventType;
    }

    public T getContent() {
      return content;
    }

    public void setContent(T content) {
      this.content = content;
    }
  }

  public enum EventType {

    READ((byte) 1, "阅读"),

    COMMENT((byte) 2, "评论"),

    LIKE((byte) 3, "喜欢");

    private byte value;

    private String desc;

    EventType(byte value, String desc) {
      this.value = value;
      this.desc = desc;
    }

    public byte getValue() {
      return value;
    }

    public void setValue(byte value) {
      this.value = value;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }
  }

}
