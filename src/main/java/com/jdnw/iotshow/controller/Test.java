package com.jdnw.iotshow.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdnw.iotshow.IotShowApplication;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * AUTHOR: zxy
 * com.jdnw.iotshow.controller
 * DATE: 2017/10/30
 * TIME: 16:18
 **/
public class Test {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        String end = format.format(cal.getTime());
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        cal.add(Calendar.DAY_OF_WEEK, -19);//取当前日期的前一天.
        String start = format.format(cal.getTime());
        System.out.println(start);
        System.out.println(end);
    }
}
