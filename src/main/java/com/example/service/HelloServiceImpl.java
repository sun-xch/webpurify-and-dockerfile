package com.example.service;

import com.example.entity.UserEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;


@Component
public class HelloServiceImpl{

    @Autowired
    private MongoTemplate mongoTemplate;

    public String webpurify(Map<String,String> map, String url,String type) {

        //调用发送http请求的方法
        String result = null;

        try {
            if("text".equals(type)){
                result = textFilter(map,url);
            }else if("image".equals(type)){
                result = imgFilter();
            }else if("video".equals(type)){

            }

            Query query1 = new Query(Criteria.where("_id").is("6mmWohM7ze7PdtPYB"));
            UserEntity user =  mongoTemplate.findOne(query1 , UserEntity.class,"users");
            //UserEntity user =  mongoTemplate.findOne(query1 , UserEntity.class);
            System.out.println(user.get_id());

            Query query = new Query(Criteria.where("_id").is("6mmWohM7ze7PdtPYB"));
            Update update = new Update().set("moderation", "asx");
            mongoTemplate.updateMulti(query,update,"users");

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;

    }

    public String webpurify_image(String status){

        return status;
    }

    //文字过滤
    public String textFilter(Map<String,String> map, String url) throws IOException, JSONException {

        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<String,String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println(result);

        JSONObject jsonObject = new JSONObject(result);
        System.out.println( jsonObject.get("rsp"));
        JSONObject jsonObject_1 = new JSONObject(jsonObject.get("rsp").toString());
        System.out.println( jsonObject_1.get("found"));

        return result;

    }

    //图片过滤
    public String imgFilter(){

        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        String url  = "http://im-api1.webpurify.com/services/rest/";
        Map<String,String> map = new HashMap<String,String>();
        map.put("method","webpurify.live.imgcheck");
        map.put("api_key","cdff78dd9b550e55b8d35a13f2af56ef");
        map.put("imgurl","http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1306/21/c1/22391516_1371821651050_800x600.jpg");
        map.put("format","json");
        map.put("customimgid","123");
        map.put("callback ","http://localhost:8080/v2/moderation/callback/webpurify");

        try{
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<String,String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println(result);

        return result;

    }


}
