package com.jdnw.iotshow.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdnw.iotshow.util.XmlUtil;
import com.scinfo.aep.sdk.general.impl.GeneralRequestImpl;
import com.scinfo.entity.AppHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * AUTHOR: zxy
 * com.jdnw.iotshow.controller
 * DATE: 2017/10/30
 * TIME: 15:43
 **/
@Controller
public class RightController {
    private static int flag = 0;

    @Value("${iotshow.appkey}")
    private String appKey;

    @Value("${iotshow.appid}")
    private String appId;
    /**
     * 设备数量每日趋势
     * @return
     */
    @RequestMapping("/commonSgnlAllCntByDay")
    @ResponseBody
    public String commonSgnlCntByDay() throws Exception {
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        String end = format.format(cal.getTime());
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        cal.add(Calendar.DAY_OF_WEEK, -19);//取当前日期的前一天.
        String start = format.format(cal.getTime());
        String result = null;
        if (flag ==0){
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonSgnlAllCntByDay");
            Map<String, String> map = new HashMap<String, String>();
            map.put("USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            map.put("START", start);
            map.put("END", end);
            String xml = gr.sendSoapReq(ah, map);
            result = XmlUtil.xml2JSON(xml.getBytes()).toJSONString();
            return result;
        }else {
            return commonSgnlCntByDayDemoData();
        }

        //return "{\"result\":{\"object\":[{\"DATE_CD\":\"20171012\",\"SGNL_CNT\":\"158\"},{\"DATE_CD\":\"20171013\",\"SGNL_CNT\":\"3934\"},{\"DATE_CD\":\"20171014\",\"SGNL_CNT\":\"13775\"},{\"DATE_CD\":\"20171015\",\"SGNL_CNT\":\"13895\"},{\"DATE_CD\":\"20171016\",\"SGNL_CNT\":\"13731\"},{\"DATE_CD\":\"20171017\",\"SGNL_CNT\":\"13506\"},{\"DATE_CD\":\"20171018\",\"SGNL_CNT\":\"57802\"},{\"DATE_CD\":\"20171020\",\"SGNL_CNT\":\"14\"},{\"DATE_CD\":\"20171023\",\"SGNL_CNT\":\"459\"},{\"DATE_CD\":\"20171024\",\"SGNL_CNT\":\"7303\"},{\"DATE_CD\":\"20171025\",\"SGNL_CNT\":\"86376\"},{\"DATE_CD\":\"20171026\",\"SGNL_CNT\":\"38977\"},{\"DATE_CD\":\"20171027\",\"SGNL_CNT\":\"186515\"}]}}";
        //
    }
    @RequestMapping("/commonAblitiesCntByDay")
    @ResponseBody
    public String commonAblitiesCntByDay() throws Exception {
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        String end = format.format(cal.getTime());
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        cal.add(Calendar.DAY_OF_WEEK, -9);//取当前日期的前一天.
        String start = format.format(cal.getTime());
        String result = null;
        if (flag ==0){
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonAbilityTrendDay");
            Map<String, String> map = new HashMap<String, String>();
            map.put("VC_USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            map.put("START", start);
            map.put("END", end);
            String xml = gr.sendSoapReq(ah, map);
            result = XmlUtil.xml2JSON(xml.getBytes()).toJSONString();
            return result;
        }else {
            return commonSgnlCntByDayDemoData();
        }
    }
    @RequestMapping("/commonAppRankCntByDay")
    @ResponseBody
    public String commonAppRankCntByDay() throws Exception {
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        String end = format.format(cal.getTime());
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        String result = null;
        if (flag ==0){
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonAppCallCntByDay");
            Map<String, String> map = new HashMap<String, String>();
            map.put("VC_USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            map.put("LIMIT_NUM", "5");
            map.put("DATE_CD", end);
            String xml = gr.sendSoapReq(ah, map);
            result = XmlUtil.xml2JSON(xml.getBytes()).toJSONString();
            return result;
        }else {
            return commonSgnlCntByDayDemoData();
        }
        //return "{\"result\":{\"object\":[{\"VC_APP_NAME\":\"售电\",\"INT_CALL_CNT\":\"10000\"},{\"VC_APP_NAME\":\"脚手架\",\"INT_CALL_CNT\":\"13731\"},{\"VC_APP_NAME\":\"LNG点供\",\"INT_CALL_CNT\":\"13506\"},{\"VC_APP_NAME\":\"雪亮工程\",\"INT_CALL_CNT\":\"28652\"},{\"VC_APP_NAME\":\"智能家居1\",\"INT_CALL_CNT\":\"18652\"}]}}";
    }
    private String commonSgnlCntByDayDemoData(){
        Calendar cal1 = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        String lastDate = format.format(cal1.getTime());
        String lastData = String.valueOf(cal1.getTime().getTime()).substring(6,10);
        int i=0;
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        while (i<19){
            Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
            HashMap<String,Object> hashMap = new HashMap<String, Object>();
            cal.add(Calendar.DAY_OF_WEEK, (-19+i));//取当前日期的前一天.
            String date = format.format(cal.getTime());
            if (i%2==0){
                hashMap.put("SGNL_CNT",10000+i*111-7000);
            }else{
                hashMap.put("SGNL_CNT",10000-i*11-7000+i*111);
            }
            hashMap.put("DATE_CD",date);
            list.add(hashMap);
            i++;
        }
        HashMap<String,Object> mp = new HashMap<String, Object>();
        mp.put("DATE_CD",lastDate);
        mp.put("SGNL_CNT",lastData);
        list.add(mp);
        HashMap<String,Object> objet = new HashMap<String,Object>();
        objet.put("object",list);
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("result",objet);
        return JSONObject.toJSON(result).toString();
    }
}
