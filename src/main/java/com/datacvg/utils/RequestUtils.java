package com.datacvg.utils;

import com.alibaba.fastjson.JSONArray;
import com.datacvg.config.RequestParams;
import com.datacvg.service.serviceImpl.ReaderConfigImpl;
import com.datacvg.service.serviceImpl.WriterConfigImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.util.Map;


/**
 * 请求类
 */
public class RequestUtils {

    public static void httpRequest(String type,String url,String path,
                            String contentType,String request,String expect,String isLoggin){
        HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(RequestParams.cookieStore).build();
        if (type.equals("POST")||type.equals("Post")||type.equals("post")){
            //进行POST请求
            allPost(httpClient,type,url,path,contentType,request,expect,isLoggin);
        }else {
            allGet(httpClient,type,url,path,contentType,request,expect,isLoggin);
        }

    }

    /**
     * Post请求
     * @param httpClient
     * @param type
     * @param url
     * @param path
     * @param contentType
     * @param request
     * @param expect
     * @param isLoggin
     */
    public static void allPost(HttpClient httpClient, String type, String url, String path,
                               String contentType, String request, String expect, String isLoggin){
        String flag = "";
        HttpPost httpPost = new HttpPost(url+path);
        if(isLoggin.contains("Yes")||isLoggin.contains("YES")||isLoggin.contains("yes")||isLoggin.contains("True")||isLoggin.contains("true")){
            //根据系统实际情况进行调整
            ReaderConfigImpl rc = new ReaderConfigImpl();
            httpPost.setHeader("userId",rc.reader("userId"));
            httpPost.setHeader("token",rc.reader("token"));
        }
        httpPost.setHeader("Content-Type",contentType);
        StringEntity entity = new StringEntity(request.toString(),"utf-8");
        httpPost.setEntity(entity);
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String res = EntityUtils.toString(httpResponse.getEntity());
            if(res.contains(expect)){
                flag = "true";
            }

            if(flag.equals("true")){
                writeParameter(res);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        Assert.assertEquals("true",flag);
        Assert.assertEquals(flag,"true");
    }


    /**
     * Get请求
     * @param httpClient
     * @param type
     * @param url
     * @param path
     * @param contentType
     * @param request
     * @param expect
     * @param isLoggin
     */
    public static void allGet(HttpClient httpClient, String type, String url, String path,
                              String contentType, String request, String expect, String isLoggin){
        String flag = "";
        HttpGet httpGet;
        if(request.length()!=0){
            httpGet = new HttpGet(url+path+"?"+request);
        }else{
            httpGet = new HttpGet(url+path);
        }
        if(isLoggin.contains("Yes")||isLoggin.contains("YES")||isLoggin.contains("yes")||isLoggin.contains("True")||isLoggin.contains("true")){
            //根据系统实际情况进行调整
            ReaderConfigImpl rc = new ReaderConfigImpl();
            httpGet.setHeader("userId",rc.reader("userId"));
            httpGet.setHeader("token",rc.reader("token"));
        }
        httpGet.setHeader("Content-Type",contentType);
//        StringEntity entity = new StringEntity(request.toString(),"utf-8");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String res = EntityUtils.toString(httpResponse.getEntity());
//            System.out.println(res);
            if(isLoggin.equals(false)){
                Map<String,Object> map = JSONArray.parseObject(res);
                Assert.assertEquals(map.get("success").toString(),"true");
                //token取json.message
                RequestParams.token = map.get("message").toString();
                //获取登录data信息
                Map<String,Object> data_map = JSONArray.parseObject(map.get("data").toString());
                RequestParams.userId = data_map.get("userId").toString();
            }
            if(res.contains(expect)){
                flag = "true";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(flag,"true");
    }

    /**
     * 存放登录成功后，写入文本的必要内容
     * @param res
     */
    public static void writeParameter(String res){
        Map<String,Object> map = JSONArray.parseObject(res);
        Assert.assertEquals(map.get("success").toString(),"true");
        //token取json.message
        RequestParams.token = map.get("message").toString();
        //获取登录data信息
        Map<String,Object> data_map = JSONArray.parseObject(map.get("data").toString());
        RequestParams.userId = data_map.get("userId").toString();
        WriterConfigImpl wc = new WriterConfigImpl();
        wc.writer("token",RequestParams.token);
        wc.writer("userId",RequestParams.userId);
    }

}