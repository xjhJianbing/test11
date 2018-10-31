package com.jianbing.Config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestConfig {
    public static String loginUrl;
    //获取用户信息接口uri
    public static String getUserLoanInfoUrl;

    public static DefaultHttpClient defaultHttpClient;
    public static CookieStore store;
    public static String token;
}
