package com.jianbing.cases.yinni;

import com.jianbing.Config.TestConfig;
import com.jianbing.model.InterfaceName;
import com.jianbing.model.SysUser;
import com.jianbing.utils.ConfigFile;
import com.jianbing.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.testng.Assert.assertEquals;

public class Login {
    private String url;
    private ResourceBundle bundle;
    private String Token;

    @BeforeTest(groups = "loginTrue",description = "测试准备工作,获取HttpClient对象")
    public void beforeTest(){

        TestConfig.defaultHttpClient = new DefaultHttpClient();
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.getUserLoanInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERLOANINFO);


        //bundle = ResourceBundle.getBundle("application",Locale.CHINA);
        //url=bundle.getString("yn.url");
    }

    @Test(groups = "loginTrue",description = "用户成功登陆接口")
    public void loginSuccess() throws IOException {
        SqlSession session = DataBaseUtil.getSqlsession();
        SysUser sysUser = session.selectOne("sys_user",39);
        System.out.println(sysUser.toString());
        System.out.println(TestConfig.loginUrl);


        //下边的代码为写完接口的测试代码
        Boolean result = getResult(sysUser);
        //处理结果，就是判断返回结果是否符合预期
        assertEquals(result,Boolean.TRUE);
        //testsssss
     }


    private Boolean getResult(SysUser sysUser) throws IOException {
        //下边的代码为接口测试代码
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("phone",sysUser.getPhone());
        param.put("valid_code",123456);
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
         JSONObject resultJson = new JSONObject(result);
        Token=resultJson.getString("ret");
        System.out.println("token"+Token);
        TestConfig.token=Token;

        Boolean success = resultJson.getBoolean("success");
        return success;
    }
    }

