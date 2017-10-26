package com.jdnw.iotshow.controller;

import ch.qos.logback.core.joran.spi.XMLUtil;
import com.jdnw.iotshow.util.SoapClient;
import com.jdnw.iotshow.util.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            String soapResponseData = soapClient.invoke(patameterMap);
            result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}