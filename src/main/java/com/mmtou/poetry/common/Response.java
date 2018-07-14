package com.mmtou.poetry.common;

import java.io.Serializable;

public class Response<T> implements Serializable {

  private boolean success = true;
  private String code;
  private T result;
  private String message;
  private Page page;

  private static final String SUCCESS_CODE = "200";
  private static final String SUCCESS_MESSAGE = "成功";

  public static <T> Response<T> success() {
    return new Response(true, SUCCESS_CODE, SUCCESS_MESSAGE, (Object)null, (Page)null);
  }

  public static <T> Response<T> success(T result) {
    return new Response(true, SUCCESS_CODE, SUCCESS_MESSAGE, result, (Page)null);
  }

  public static <T> Response<T> success(T result, Page page) {
    return new Response(true, SUCCESS_CODE, SUCCESS_MESSAGE, result, page);
  }

  public static <T> Response<T> fail(String code, String message) {
    return new Response(false, code, message, (Object)null, (Page)null);
  }

  public Response<T> page(Page page) {
    this.setPage(page);
    return this;
  }

  public Response(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public Response(boolean success, String code, String message, T result, Page page) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.result = result;
    this.page = page;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getResult() {
    return this.result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public Page getPage() {
    return this.page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

}
