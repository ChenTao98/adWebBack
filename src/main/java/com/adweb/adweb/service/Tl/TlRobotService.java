package com.adweb.adweb.service.Tl;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TlRobotService {
    public String sentMassage(String message,String openId){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("reqType",0);
        jsonObject.put("perception",new Perception(message));
        jsonObject.put("userInfo",new UserInfo(openId));
//        return jsonObject.toJSONString();
        HttpClient httpClient= HttpClientBuilder.create().build();
        HttpPost httpPost=new HttpPost("http://openapi.tuling123.com/openapi/api/v2");
        StringEntity stringEntity;
        try {
            stringEntity = new StringEntity(jsonObject.toJSONString());
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            HttpResponse res = httpClient.execute(httpPost);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                return EntityUtils.toString(res.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
