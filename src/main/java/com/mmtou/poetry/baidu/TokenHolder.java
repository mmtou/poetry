package com.mmtou.poetry.baidu;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * token的获取类
 * 将apiKey和secretKey换取token，注意有效期保存在expiresAt
 */
public class TokenHolder {

  private static Logger log = LoggerFactory.getLogger(TokenHolder.class);

  public static final String ASR_SCOPE = "audio_voice_assistant_get";

  public static final String TTS_SCOPE = "audio_tts_post";

  /**
   * url , Token的url，http可以改为https
   */
  private static final String url = "http://openapi.baidu.com/oauth/2.0/token";

  /**
   * asr的权限 scope 是  "audio_voice_assistant_get"
   * tts 的权限 scope 是 "audio_tts_post"
   */
  private String scope;

  /**
   * 网页上申请语音识别应用获取的apiKey
   */
  private String apiKey;

  /**
   * 网页上申请语音识别应用获取的secretKey
   */
  private String secretKey;

  /**
   * 保存访问接口获取的token
   */
  private String token;

  /**
   * 当前的时间戳，毫秒
   */
  private long expiresAt;

  /**
   * @param apiKey 网页上申请语音识别应用获取的apiKey
   * @param secretKey 网页上申请语音识别应用获取的secretKey
   */
  public TokenHolder(String apiKey, String secretKey, String scope) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
    this.scope = scope;
  }


  /**
   * 获取token，refresh 方法后调用有效
   */
  public String getToken() {
    return token;
  }

  /**
   * 获取过期时间，refresh 方法后调用有效
   */
  public long getExpiresAt() {
    return expiresAt;
  }


  /**
   * 获取token
   *
   * @throws IOException http请求错误
   * @throws BaiduException http接口返回不是 200, access_token未获取
   */
  public void refresh() throws IOException, BaiduException {
    String getTokenURL = url + "?grant_type=client_credentials"
        + "&client_id=" + ConnUtil.urlEncode(apiKey) + "&client_secret=" + ConnUtil
        .urlEncode(secretKey);

    log.info("获取token request: {}", getTokenURL);
    URL url = new URL(getTokenURL);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setConnectTimeout(5000);
    String result = ConnUtil.getResponseString(conn);
    log.info("获取token response: {}", result);
    parseJson(result);
  }

  /**
   * @param result token接口获得的result
   */
  private void parseJson(String result) throws BaiduException {
    Map map = JSON.parseObject(result, Map.class);
    if (map.get("access_token") == null) {
      // 返回没有access_token字段
      throw new BaiduException("access_token not obtained, " + result);
    }
    if (map.get("scope") == null) {
      // 返回没有scope字段
      throw new BaiduException("scopenot obtained, " + result);
    }
    if (!map.get("scope").toString().contains(scope)) {
      throw new BaiduException("scope not exist, " + scope + "," + result);
    }
    token = map.get("access_token").toString();
    expiresAt =
        System.currentTimeMillis() + Long.parseLong(map.get("expires_in").toString()) * 1000;
  }
}
