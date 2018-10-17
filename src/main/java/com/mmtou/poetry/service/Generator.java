package com.mmtou.poetry.service;

import com.google.common.collect.Lists;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.promeg.pinyinhelper.Pinyin;
import com.mmtou.poetry.entity.Poetry;
import com.mmtou.poetry.entity.PoetryExtendWithBLOBs;
import com.mmtou.poetry.entity.User;
import com.mmtou.poetry.entity.UserExample;
import com.mmtou.poetry.entity.UserExtend;
import com.mmtou.poetry.mapper.PoetryDAO;
import com.mmtou.poetry.mapper.PoetryExtendDAO;
import com.mmtou.poetry.mapper.UserDAO;
import com.mmtou.poetry.mapper.UserExtendDAO;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

@Service
public class Generator {

  private static Logger log = LoggerFactory.getLogger(Generator.class);

  CloseableHttpClient httpclient = HttpClients.createDefault();

  @Autowired
  private PoetryDAO poetryDAO;
  @Autowired
  private PoetryExtendDAO poetryExtendDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private UserExtendDAO userExtendDAO;

  public void generate() throws IOException {
    boolean haveRecord = true;
    int page = 1;
    do {
      HttpPost httpPost = new HttpPost(
          "https://9pq709je.engine.lncld.net/1.1/call/getWorksAllIncludeCount");
      List<NameValuePair> nvps = Lists.newArrayList();
      nvps.add(new BasicNameValuePair("page", String.valueOf(page)));
      nvps.add(new BasicNameValuePair("perPage", "100"));
      httpPost.setEntity(new UrlEncodedFormEntity(nvps));
      httpPost.setHeader("X-LC-Id", "9pq709je4y36ubi10xphdpovula77enqrz27idozgry7x644");
      httpPost.setHeader("X-LC-Sign", "d0ffcf356fe0be2da964a2137794f1b3,1539671720072");
      CloseableHttpResponse response = httpclient.execute(httpPost);

      try {
        String responseStr = EntityUtils.toString(response.getEntity());
        System.out.println(responseStr);

        Map map = JSON.parseObject(responseStr, Map.class);
        Object resultObj = map.get("result");
        if (resultObj != null) {
          Map resultMap = JSON.parseObject(resultObj.toString(), Map.class);
          Object works = resultMap.get("works");
          if (works != null) {
            List<PoetryInfo> list = JSON
                .parseObject(works.toString(), new TypeReference<List<PoetryInfo>>() {
                });
            if (list.size() == 0) {
              haveRecord = false;
            } else {
              for (PoetryInfo info : list) {
                try {
                  Date date = new Date();
                  Poetry poetry = new Poetry();
                  BeanUtils.copyProperties(info, poetry);
                  poetry.setCreateTime(date);
                  poetry.setCreateBy(0l);
                  poetry.setDeleteFlag((byte) 0);
                  poetryDAO.insert(poetry);

                  PoetryExtendWithBLOBs poetryExtend = new PoetryExtendWithBLOBs();
                  poetryExtend.setPoetryId(poetry.getId());
                  poetryExtend.setAnnotation(info.getAnnotation());
                  poetryExtend.setAppreciation(info.getAppreciation());
                  poetryExtend.setIntro(info.getIntro());
                  poetryExtend.setTranslation(info.getTranslation());
                  poetryExtend.setCreateTime(date);
                  poetryExtend.setCreateBy(0l);
                  poetryExtend.setDeleteFlag((byte) 0);
                  poetryExtendDAO.insert(poetryExtend);

                  User user = userDAO.selectByPrimaryKey(info.getAuthorId());
                  if (user == null) {
                    user = new User();
                    user.setId(info.getAuthorId());
                    user.setName(info.getAuthorName());
                    user.setState((byte) 10);
                    user.setCreateTime(date);
                    user.setDeleteFlag((byte) 0);
                    userDAO.insert(user);

                    String desc = info.getAuthorDesc();

                    if (StringUtils.isBlank(desc)) {
                      CloseableHttpResponse response2 = null;
                      try {
                        httpPost = new HttpPost(
                            "https://9pq709je.engine.lncld.net/1.1/call/getAuthorById2");
                        nvps = Lists.newArrayList();
                        nvps.add(
                            new BasicNameValuePair("authorId", info.getAuthor().get("objectId")));
                        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                        httpPost
                            .setHeader("X-LC-Id",
                                "9pq709je4y36ubi10xphdpovula77enqrz27idozgry7x644");
                        httpPost
                            .setHeader("X-LC-Sign",
                                "d0ffcf356fe0be2da964a2137794f1b3,1539671720072");
                        response2 = httpclient.execute(httpPost);

                        responseStr = EntityUtils.toString(response2.getEntity());
                        map = JSON.parseObject(responseStr, Map.class);
                        resultMap = JSON.parseObject(map.get("result").toString(), Map.class);
                        if (resultMap != null) {
                          desc = resultMap.get("desc") == null ? null
                              : resultMap.get("desc").toString();
                        }
                      } catch (Exception e) {
                        log.error("作者异常: 原始内容: {}", responseStr, e);
                      } finally {
                        if (response2 != null) {
                          response2.close();
                        }
                      }
                    }

                    UserExtend userExtend = new UserExtend();
                    userExtend.setUserId(user.getId());
                    userExtend.setDescription(desc);
                    userExtend.setCreateTime(date);
                    userExtend.setDeleteFlag((byte) 0);
                    userExtendDAO.insert(userExtend);
                  }
                } catch (Exception e) {
                  log.error("诗词异常: 原始内容: {}", JSON.toJSONString(info), e);
                }
              }
            }
          }
        }
      } finally {
        response.close();
        page++;
      }
    } while (haveRecord);
  }

  public void getAvatar()
      throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
      // 默认信任所有证书
      public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        return true;
      }
    }).build();
    // AllowAllHostnameVerifier: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
    SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext,
        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    CloseableHttpClient httpClientSSL = HttpClients.custom().setSSLSocketFactory(sslcsf).build();

    List<User> users = userDAO.selectByExample(new UserExample());
    for (User user : users) {
      try {
        String name = Pinyin.toPinyin(user.getName(), "").toLowerCase();
        long id = user.getId();

        File avatar = new File("/data/upload/avatar/" + id + ".jpg");
        avatar.createNewFile();
        OutputStream out = new FileOutputStream(avatar);

        HttpGet httpget = new HttpGet("https://www.gushiwen.com/touxiang/" + name + ".jpg");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
            .setConnectTimeout(2000).setConnectionRequestTimeout(2000).build();//设置请求和传输超时时间
        httpget.setConfig(requestConfig);
        CloseableHttpResponse response = httpClientSSL.execute(httpget);

        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
          HttpEntity entity = response.getEntity();
          entity.writeTo(out);

          user.setAvatar("/upload/avatar/" + id + ".jpg");
        } else {
          avatar.delete();
          user.setAvatar("/upload/avatar/default.png");
        }
        out.close();
        response.close();

        userDAO.updateByPrimaryKey(user);
        log.info("done: {}", name);
      } catch (Exception e) {
        log.error("exception: ", e);
      }
    }
    log.info("完成");
  }

}

class PoetryInfo {

  private String authorName;
  private long authorId;
  private String appreciation;
  private String dynasty;
  private String content;
  private String title;
  private String translation;
  private String intro;
  private String authorDesc;
  private String annotation;
  private Map<String, String> author;

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(long authorId) {
    this.authorId = authorId;
  }

  public String getAppreciation() {
    return appreciation;
  }

  public void setAppreciation(String appreciation) {
    this.appreciation = appreciation;
  }

  public String getDynasty() {
    return dynasty;
  }

  public void setDynasty(String dynasty) {
    this.dynasty = dynasty;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTranslation() {
    return translation;
  }

  public void setTranslation(String translation) {
    this.translation = translation;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public String getAuthorDesc() {
    return authorDesc;
  }

  public void setAuthorDesc(String authorDesc) {
    this.authorDesc = authorDesc;
  }

  public String getAnnotation() {
    return annotation;
  }

  public void setAnnotation(String annotation) {
    this.annotation = annotation;
  }

  public Map<String, String> getAuthor() {
    return author;
  }

  public void setAuthor(Map<String, String> author) {
    this.author = author;
  }
}
