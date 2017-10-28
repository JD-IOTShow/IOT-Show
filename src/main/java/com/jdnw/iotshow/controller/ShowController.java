package com.jdnw.iotshow.controller;

import com.jdnw.iotshow.util.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AUTHOR: zxy
 * com.jdnw.iotshow.controller
 * DATE: 2017/10/24
 * TIME: 14:36
 **/
@Controller
public class ShowController {
    private int count1 = 135095;
    private int count2 = 268070;
    private int count3 = 555;

    @Autowired
    @RequestMapping("")
    public String show(){
        return "index";
    }

    @RequestMapping("/send")
    public void sendMessage(){
    }

    @RequestMapping("/commonAbilityCallCnt")
    @ResponseBody
    public String commonAbilityCallCnt(){
        SoapClient soapClient = new SoapClient("commonAbilityCallCnt",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        Map<String, String> patameterMap = new HashMap<String, String>(2);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");

        String soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        String result = null;
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int count = this.count1++;
        result = "{\"result\":{\"object\":[{\"ABILITY_CALL_CNT\":\""+ count +"\"}]}}";

        return result;
    }

    @RequestMapping("/queryPlatStatusAll")
    @ResponseBody
    public String queryPlatStatusAll(){
        SoapClient soapClient = new SoapClient("queryPlatStatusAll",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyyMMdd");
        String today = formatter.format(new Date());
        Map<String, String> patameterMap = new HashMap<String, String>(3);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");
        patameterMap.put("DATE_CD",today);

        String soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        String result = null;
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int count = this.count2++;
        result = "{\"result\":{\"object\":[{\"sgnlCnt\":\""+ count +"\"}]}}";

        return result;
    }

    @RequestMapping("/commonCount")
    @ResponseBody
    public String commonProductCnt(){
        String result = null;
        String soapRequestData = null;
        Map<String, String> patameterMap = null;
        SoapClient soapClient = null;

        soapClient = new SoapClient("commonProductCnt",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        patameterMap = new HashMap<String, String>(3);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");
        soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        soapClient = new SoapClient("commonDeviceCnt",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        patameterMap = new HashMap<String, String>(3);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");
        soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        soapClient = new SoapClient("commonAppCnt",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        patameterMap = new HashMap<String, String>(3);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");
        soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        soapClient = new SoapClient("commonAbilityCnt",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        patameterMap = new HashMap<String, String>(3);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");
        soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int count = this.count3++;
        result = "{\"result\":{\"object\":[{\"commonProductCnt\":\""+ (int)(count+Math.random()*1000) +"\"," +
                "\"commonDeviceCnt\":\""+ (int)(count+Math.random()*1000) +"\","+
                "\"commonAppCnt\":\""+ (int)(count+Math.random()*1000) +"\","+
                "\"commonAbilityCnt\":\""+ (int)(count+Math.random()*1000) +"\""+
                "}]}}";

        return result;
    }
}