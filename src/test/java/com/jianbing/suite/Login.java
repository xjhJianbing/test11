package com.jianbing.suite;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login {
    private String url;
    private ResourceBundle bundle;
    //private CookieStore store;
    //public String Token;


    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application",Locale.CHINA);
        url=bundle.getString("yn.url");
    }

    @Test@DataProvider(name = "getToken")
    public Object[][] testGetCookie() throws IOException {

        String uri = bundle.getString("ynlogin.uri");
        String testUrl= this.url+uri;

        //声明一个Client对象 用来进行方法执行
        DefaultHttpClient client = new DefaultHttpClient();

        //声明一个方法，post方法
        HttpPost post = new HttpPost(testUrl);

        //添加参数
        JSONObject param = new JSONObject();
        param.put("phone","8611111111111");
        param.put("valid_code","123456");

        //设置请求头信息 设置header
        // post.setHeader("Content-Type","application/x-www-form-urlencoded");

        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //声明对象进行响应结果存储
        String result;

        //设置cookie信息
        // client.setCookieStore(this.cookieStore);

        //执行post方法
        HttpResponse response =client.execute(post);

        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("result:"+result);


        JSONObject resultJson = new JSONObject(result);
        String Token=resultJson.getString("ret");

        System.out.println(Token);
        System.out.println("_______________________________");
        return new Object[][]{
                {Token}
        };

    }

    }

