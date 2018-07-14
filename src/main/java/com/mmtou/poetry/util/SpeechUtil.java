package com.mmtou.poetry.util;

import com.mmtou.poetry.baidu.BaiduException;
import com.mmtou.poetry.baidu.ConnUtil;
import com.mmtou.poetry.baidu.TokenHolder;
import com.mmtou.poetry.config.AppConfig;
import com.mmtou.poetry.config.BaiduConfig;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 语音合成工具类，调用百度语音合成API
 */
public class SpeechUtil {

  private static Logger log = LoggerFactory.getLogger(SpeechUtil.class);

  /**
   * 生成语音文件
   *
   * @return 语音文件url
   */
  public static String generateSpeech(String text) {
    AppConfig appConfig = SpringUtil.getObject("appConfig", AppConfig.class);

    String baseDir = appConfig.getBaseDir();

    try {
      String uri = baiduGenerateSpeech(text, baseDir);
      String baseDownloadUrl = appConfig.getBaseDownloadUrl();
      return baseDownloadUrl + uri;
    } catch (IOException e) {
      log.error("语音合成失败: ", e);
    } catch (BaiduException e) {
      log.error("语音合成失败: ", e);
    }

    return null;
  }

  /**
   * 百度语音合成
   */
  private static String baiduGenerateSpeech(String text, String baseDir)
      throws IOException, BaiduException {
    BaiduConfig baiduConfig = SpringUtil.getObject("baiduConfig", BaiduConfig.class);

    TokenHolder holder = new TokenHolder(baiduConfig.getAppKey(), baiduConfig.getSecretKey(),
        TokenHolder.ASR_SCOPE);
    holder.refresh();
    String token = holder.getToken();

    String url = new StringBuffer(baiduConfig.getUrl()).append("?tex=")
        .append(ConnUtil.urlEncode(text))
        .append("&per=").append(baiduConfig.getPer())
        .append("&spd=").append(baiduConfig.getSpd())
        .append("&pit=").append(baiduConfig.getPit())
        .append("&vol=").append(baiduConfig.getVol())
        .append("&cuid=").append(baiduConfig.getCuid())
        .append("&tok=").append(token)
        .append("&lan=zh&ctp=1").toString();

    log.info("语音合同request: {}", url);

    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
    conn.setConnectTimeout(5000);
    String contentType = conn.getContentType();
    if (contentType.contains("mp3")) {
      byte[] bytes = ConnUtil.getResponseBytes(conn);
      String fileName = System.currentTimeMillis() + ".mp3";
      String dir = "ai" + File.separator + DateFormatUtils.format(new Date(), "yyyyMMdd");
      String filePath = dir + fileName;
      File dirFile = new File(baseDir + dir);
      if (!dirFile.exists()) {
        dirFile.mkdirs();
      }
      File file = new File(dirFile, fileName);
      FileOutputStream os = new FileOutputStream(file);
      os.write(bytes);
      os.close();

      return filePath;
    } else {
      String res = ConnUtil.getResponseString(conn);
      log.warn("语音合同response content-type: {}, response: {}", contentType, res);
      return null;
    }
  }

}
