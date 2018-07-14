package com.mmtou.poetry.baidu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * 与连接相关的Util类
 */
public class ConnUtil {

  private static Logger log = LoggerFactory.getLogger(ConnUtil.class);

  /**
   * UrlEncode， UTF-8 编码
   *
   * @param str 原始字符串
   */
  public static String urlEncode(String str) {
    String result = null;
    try {
      result = URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 从HttpURLConnection 获取返回的字符串
   */
  public static String getResponseString(HttpURLConnection conn)
      throws IOException, BaiduException {
    return new String(getResponseBytes(conn));
  }

  /**
   * 从HttpURLConnection 获取返回的bytes
   * 注意 HttpURLConnection自身问题， 400类错误，会直接抛出异常。不能获取conn.getInputStream();
   *
   * @throws IOException http请求错误
   * @throws BaiduException http 的状态码不是 200
   */
  public static byte[] getResponseBytes(HttpURLConnection conn) throws IOException, BaiduException {
    int responseCode = conn.getResponseCode();
    if (responseCode != 200) {
      log.error("请求失败: httpStatus: {}, httpMessage: {}", responseCode, conn.getResponseMessage());
      throw new BaiduException("http response code is" + responseCode);
    }

    InputStream inputStream = conn.getInputStream();
    byte[] result = getInputStreamContent(inputStream);
    return result;
  }

  /**
   * 将InputStream内的内容全部读取，作为bytes返回
   *
   * @throws IOException @see InputStream.read()
   */
  public static byte[] getInputStreamContent(InputStream is) throws IOException {
    byte[] b = new byte[1024];
    // 定义一个输出流存储接收到的数据
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    // 开始接收数据
    int len = 0;
    while (true) {
      len = is.read(b);
      if (len == -1) {
        // 数据读完
        break;
      }
      byteArrayOutputStream.write(b, 0, len);
    }
    return byteArrayOutputStream.toByteArray();
  }
}
