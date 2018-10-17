package com.mmtou.poetry.web;

import com.mmtou.poetry.common.Response;
import com.mmtou.poetry.service.Generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static com.mmtou.poetry.common.Response.success;

//@RestController
//@RequestMapping("/api/generator")
public class GeneratorController {

  @Autowired
  private Generator generator;

  @GetMapping("generate")
  public Response<Boolean> generate() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          generator.generate();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
    return success(true);
  }

  @GetMapping("getAvatar")
  public Response<Boolean> getAvatar() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          generator.getAvatar();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        } catch (KeyStoreException e) {
          e.printStackTrace();
        } catch (KeyManagementException e) {
          e.printStackTrace();
        }
      }
    }).start();
    return success(true);
  }

}
