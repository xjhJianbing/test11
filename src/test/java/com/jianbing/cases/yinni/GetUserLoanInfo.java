package com.jianbing.cases.yinni;


import com.jianbing.Config.TestConfig;
import com.jianbing.model.GetUserLoanInfoCase;
import com.jianbing.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GetUserLoanInfo {

    @Test(description = "获取userId为65的用户信息")
    public void getUserInfo() throws IOException, InterruptedException {
        SqlSession session = DataBaseUtil.getSqlsession();
        GetUserLoanInfoCase getUserLoanInfoCase = session.selectOne("sys_user_loan", 65);
        System.out.println(getUserLoanInfoCase.toString());
        System.out.println(TestConfig.getUserLoanInfoUrl);

        //下边为写完接口的代码
        JSONArray resultJson = getJsonResult(getUserLoanInfoCase);

        Thread.sleep(2000);
        if (resultJson != null) {
            String resString = (String) resultJson.get(0);
            JSONObject first = new JSONObject(resString);
            JSONObject ret =first.getJSONObject("ret");
            JSONObject loan_info = ret.getJSONObject("user_loan_info");
           // Integer loan_dayCount = loan_info.getInt("loan_daycount");
            Double amount = loan_info.getDouble("amount");
        //    System.out.println("loandayCount:"+loan_dayCount);
            System.out.println("amount API"+amount);
          //  System.out.println("day count:" + getUserLoanInfoCase.getLoan_daycount());
            System.out.println("sql amount:"+getUserLoanInfoCase.getLoan_val());

            // Assert.assertEquals(loan_dayCount,getUserLoanInfoCase.getLoan_daycount());
           // Assert.assertEquals(getUserLoanInfoCase.getLoan_daycount(),loan_dayCount.intValue());
            Assert.assertEquals(getUserLoanInfoCase.getLoan_val(),amount);
        }
    }


        private JSONArray getJsonResult(GetUserLoanInfoCase getUserInfoCase) throws IOException {
            HttpPost post = new HttpPost(TestConfig.getUserLoanInfoUrl);
            JSONObject param = new JSONObject();
            param.put("id", getUserInfoCase.getId());
            //设置请求头信息 设置header
            post.setHeader("content-type", "application/json");
            post.setHeader("Token", TestConfig.token);
            //将参数信息添加到方法中
            StringEntity entity = new StringEntity(param.toString(), "utf-8");
            post.setEntity(entity);
            //设置cookies
            TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
            //声明一个对象来进行响应结果的存储
            String result;
            //执行post方法
            HttpResponse response = TestConfig.defaultHttpClient.execute(post);
            //获取响应结果
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println("调用接口result:" + result);
            List resultList = Arrays.asList(result);
            JSONArray array = new JSONArray(resultList);
            System.out.println(array.toString());
            return array;
        }

}
