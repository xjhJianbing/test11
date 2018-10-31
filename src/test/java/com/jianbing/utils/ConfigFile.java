package com.jianbing.utils;

import com.jianbing.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    public static ResourceBundle bundle = ResourceBundle.getBundle("application",Locale.CHINA);
    public static String getUrl(InterfaceName name){
        String address = bundle.getString("yn.url");
        String uri = "";
        //最终测试地址
        String testUrl;


        if (name == InterfaceName.LOGIN){
            uri = bundle.getString("ynlogin.uri");
        }

        if (name == InterfaceName.GETUSERLOANINFO){
            uri = bundle.getString("yngetUserloanInfo.uri");
        }

        testUrl = address + uri;

        return testUrl;
    }

}