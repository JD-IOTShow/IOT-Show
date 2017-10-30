package com.jdnw.iotshow.controller;

import com.alibaba.fastjson.JSONObject;
import com.jdnw.iotshow.util.SoapClient;
import com.jdnw.iotshow.util.XmlUtil;
import com.scinfo.aep.sdk.general.impl.GeneralRequestImpl;
import com.scinfo.entity.AppHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private boolean mockData = false;
    private String appId = "122";
    private String appKey = "1715vK65u6d3aQ8";

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
    public String commonAbilityCallCnt() throws Exception {
        String result = null;
        if(!mockData) {
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonAbilityCallCnt");
            Map<String, String> map = new HashMap<String, String>();
            map.put("VC_USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            String xml = gr.sendSoapReq(ah, map);
            result = XmlUtil.xml2JSON(xml.getBytes()).toJSONString();;
        }else {
            int count = this.count1++;
            result = "{\"result\":{\"object\":[{\"ABILITY_CALL_CNT\":\"" + count + "\"}]}}";
        }
        return result;
    }

    @RequestMapping("/queryPlatStatusAll")
    @ResponseBody
    public String queryPlatStatusAll() throws Exception {
        String result = null;
        if(!mockData) {
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("queryPlatStatusAll");
            Map<String, String> map = new HashMap<String, String>();
            SimpleDateFormat formatter;
            formatter = new SimpleDateFormat ("yyyyMMdd");
            String today = formatter.format(new Date());
            map.put("DATE_CD", today);
            String xml = gr.sendSoapReq(ah, map);
            result = XmlUtil.xml2JSON(xml.getBytes()).toJSONString();;
        }else {
            int count = this.count2++;
            result = "{\"result\":{\"object\":[{\"SGNL_CNT\":\""+ count +"\"}]}}";
        }
        return result;
    }

    @RequestMapping("/commonCount")
    @ResponseBody
    public String commonProductCnt() throws Exception {
        String result = null;
        if(!mockData) {
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonProductCnt");
            Map<String, String> map = new HashMap<String, String>();
            map.put("USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            String xml = gr.sendSoapReq(ah, map);
            JSONObject jsonObject = XmlUtil.xml2JSON(xml.getBytes());
            String commonProductCnt = jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("PRODUCT_CNT");

            gr = new GeneralRequestImpl();
            ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonDeviceCnt");
            map = new HashMap<String, String>();
            map.put("USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            xml = gr.sendSoapReq(ah, map);
            jsonObject = XmlUtil.xml2JSON(xml.getBytes());
            String commonDeviceCnt = jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("ONLINE_DEVICE_CNT")
                    + jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("OFFLINE_DEVICE_CNT");

            gr = new GeneralRequestImpl();
            ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonAppCnt");
            map = new HashMap<String, String>();
            map.put("VC_USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            xml = gr.sendSoapReq(ah, map);
            jsonObject = XmlUtil.xml2JSON(xml.getBytes());
            String commonAppCnt = jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("APP_CNT");

            gr = new GeneralRequestImpl();
            ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonAbilityCnt");
            map = new HashMap<String, String>();
            map.put("VC_USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            xml = gr.sendSoapReq(ah, map);
            jsonObject = XmlUtil.xml2JSON(xml.getBytes());
            String commonAbilityCnt = jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("ABILITY_CNT");

            result = "{\"result\":{\"object\":[{\"commonProductCnt\":\""+ commonProductCnt +"\"," +
                    "\"commonDeviceCnt\":\""+ commonDeviceCnt +"\","+
                    "\"commonAppCnt\":\""+ commonAppCnt +"\","+
                    "\"commonAbilityCnt\":\""+ commonAbilityCnt +"\""+
                    "}]}}";
        }else {
            int count = this.count3++;
            result = "{\"result\":{\"object\":[{\"commonProductCnt\":\""+ (int)(count+Math.random()*1000) +"\"," +
                    "\"commonDeviceCnt\":\""+ (int)(count+Math.random()*1000) +"\","+
                    "\"commonAppCnt\":\""+ (int)(count+Math.random()*1000) +"\","+
                    "\"commonAbilityCnt\":\""+ (int)(count+Math.random()*1000) +"\""+
                    "}]}}";
        }
        return result;
    }

    @RequestMapping("/onlineRate")
    @ResponseBody
    public String onlineRate() throws Exception {
        String result = null;
        if(!mockData) {
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonDeviceCnt");
            Map<String, String> map = new HashMap<String, String>();
            map.put("USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            String xml = gr.sendSoapReq(ah, map);
            JSONObject jsonObject = XmlUtil.xml2JSON(xml.getBytes());
            double ONLINE_DEVICE_CNT = Double.parseDouble(jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("ONLINE_DEVICE_CNT"));
            double OFFLINE_DEVICE_CNT = Double.parseDouble(jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("OFFLINE_DEVICE_CNT"));
            double onlineRate = ONLINE_DEVICE_CNT/(ONLINE_DEVICE_CNT + OFFLINE_DEVICE_CNT);
            result = "{\"result\":{\"object\":[{\"onlineRate\":\""+ onlineRate +"\"}]}}";
        }else {
            double onlineRate = Math.random();
            result = "{\"result\":{\"object\":[{\"onlineRate\":\""+ onlineRate +"\"}]}}";
        }
        return result;
    }

    @RequestMapping("/successRate")
    @ResponseBody
    public String successRate() throws Exception {
        String result = null;
        if(!mockData) {
            GeneralRequestImpl gr = new GeneralRequestImpl();
            AppHeader ah = new AppHeader();
            ah.setAppId(appId);
            ah.setAppKey(appKey);
            ah.setAbilityCode("commonAbilityCallCntByDate");
            Map<String, String> map = new HashMap<String, String>();
            SimpleDateFormat formatter;
            formatter = new SimpleDateFormat ("yyyyMMdd");
            String today = formatter.format(new Date());
            map.put("DATE_CD", today);
            map.put("VC_USER_ID", "ALL");
            map.put("TENANT_ID", "ALL");
            String xml = gr.sendSoapReq(ah, map);
            JSONObject jsonObject = XmlUtil.xml2JSON(xml.getBytes());

            double ABILITY_CALL_CNT = Double.parseDouble(jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("ABILITY_CALL_CNT"));
            double ABILITY_CALL_SUCC_CNT = Double.parseDouble(jsonObject.getJSONObject("result").getJSONArray("object")
                    .getJSONObject(0).getString("ABILITY_CALL_SUCC_CNT"));
            double successRate = ABILITY_CALL_SUCC_CNT/ABILITY_CALL_CNT;
            result = "{\"result\":{\"object\":[{\"successRate\":\""+ successRate +"\"}]}}";
        }else {
            double successRate = Math.random();
            result = "{\"result\":{\"object\":[{\"successRate\":\""+ successRate +"\"}]}}";
        }
        return result;
    }

    class CityData{
        private String cityName;
        private String coordinate;
        private int deviceCount;
        public CityData(String cityName, String coordinate, int deviceCount) {
            this.cityName = cityName;
            this.coordinate = coordinate;
            this.deviceCount = deviceCount;
        }
    }

    private ArrayList<CityData> transportDataSource = new ArrayList<CityData>();
    {
        this.transportDataSource.add(new CityData("长春", "[125.8154, 44.2584]",23));
        this.transportDataSource.add(new CityData("长沙", "[113.0823, 28.2568]",14));
        this.transportDataSource.add(new CityData("贵阳", "[106.6992, 26.7682]",56));
        this.transportDataSource.add(new CityData("西安", "[109.1162, 34.2004]",52));
        this.transportDataSource.add(new CityData("济南", "[117.1582, 36.8701]",46));
        this.transportDataSource.add(new CityData("海口", "[110.3893, 19.8516]",23));
        this.transportDataSource.add(new CityData("沈阳", "[123.1238, 42.1216]",24));
        this.transportDataSource.add(new CityData("武汉", "[114.3896, 30.6628]",62));
        this.transportDataSource.add(new CityData("昆明", "[102.9199, 25.4663]",54));
        this.transportDataSource.add(new CityData("杭州", "[119.5313, 29.8773]",47));
        this.transportDataSource.add(new CityData("成都", "[103.9526, 30.7617]",13));
        this.transportDataSource.add(new CityData("拉萨", "[91.1865, 30.1465]",15));
        this.transportDataSource.add(new CityData("赤峰", "[118.87, 42.28]",29));
        this.transportDataSource.add(new CityData("合肥", "[117.29, 32.0581]",35));
        this.transportDataSource.add(new CityData("呼和浩特", "[111.4124, 40.4901]",46));
        this.transportDataSource.add(new CityData("哈尔滨", "[127.9688, 45.368]",38));
        this.transportDataSource.add(new CityData("银川", "[106.27, 38.47]",27));
        this.transportDataSource.add(new CityData("南京", "[118.8062, 31.9208]",42));
        this.transportDataSource.add(new CityData("南宁", "[108.479, 23.1152]",54));
        this.transportDataSource.add(new CityData("南昌", "[116.0046, 28.6633]",33));
        this.transportDataSource.add(new CityData("乌鲁木齐", "[87.9236, 43.5883]",45));
        this.transportDataSource.add(new CityData("克拉玛依", "[84.77, 45.59]",31));
        this.transportDataSource.add(new CityData("吐鲁番", "[89.19, 42.91]",31));
        this.transportDataSource.add(new CityData("西安", "[109.1162, 34.2004]",41));
        this.transportDataSource.add(new CityData("酒泉", "[98.5, 39.71]",36));
        this.transportDataSource.add(new CityData("兰州", "[103.73, 36.03]",14));
        this.transportDataSource.add(new CityData("林芝", "[94.25, 29.59]",27));
        this.transportDataSource.add(new CityData("墨脱", "[95.26, 29.22]",15));
        this.transportDataSource.add(new CityData("西宁", "[101.74, 36.56]",31));
        this.transportDataSource.add(new CityData("玉树", "[96.97, 33.03]",29));

        this.transportDataSource.add(new CityData("汕尾", "[115.375279, 22.786211]",23));
        this.transportDataSource.add(new CityData("深圳", "[114.54, 22.54]",41));
        this.transportDataSource.add(new CityData("中山", "[113.38, 22.52]",22));
        this.transportDataSource.add(new CityData("珠海", "[113.52, 22.3]",14));
        this.transportDataSource.add(new CityData("佛山", "[113.11, 23.05]",35));
        this.transportDataSource.add(new CityData("肇庆", "[112.44, 23.05]",16));
        this.transportDataSource.add(new CityData("茂名", "[110.88, 21.68]",11));
        this.transportDataSource.add(new CityData("韶关", "[113.62, 24.84]",25));
        this.transportDataSource.add(new CityData("阳江", "[111.95, 21.85]",38));
        this.transportDataSource.add(new CityData("湛江", "[110.24, 21.11]",27));

    }

    private ArrayList<CityData> transportDataDestination = new ArrayList<CityData>();
    {
        this.transportDataDestination.add(new CityData("广州", "[113.23, 23.16]",84));
        this.transportDataDestination.add(new CityData("北京", "[116.4551, 40.2539]",95));
        this.transportDataDestination.add(new CityData("上海", "[121.4648, 31.2891]",91));
        this.transportDataDestination.add(new CityData("重庆", "[106.33, 29.35]",88));
    }

    @RequestMapping("/transportMap")
    @ResponseBody
    public String transportMap(){
        SoapClient soapClient = new SoapClient("transportMap",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        Map<String, String> patameterMap = new HashMap<String, String>(2);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");

        String soapRequestData = soapClient.buildRequestData(patameterMap);
        //System.out.println(soapRequestData);
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        result.append("{\"result\":{\"object\":[");
        for(int i=0; i<2; i++){
            int indexDestination = (int)(Math.random()*4);
            CityData destination = this.transportDataDestination.get(indexDestination);
            for(int j=0; j<20; j++){
                int indexSource = (int)(Math.random()*40);
                CityData source = this.transportDataSource.get(indexSource);
                result.append("["
                        +"{\"cityName\":" + "\"" + source.cityName + "\""
                        +",\"coordinate\":" + "" + source.coordinate + ""
                        +",\"deviceCount\":" + "\"" + source.deviceCount + "\""
                        +"},"
                        +"{\"cityName\":" + "\"" + destination.cityName + "\""
                        +",\"coordinate\":" + "" + destination.coordinate + ""
                        +",\"deviceCount\":" + "\"" + destination.deviceCount + "\""
                        +"}"
                    +"]"
                );
                if(i!=1 || j!=19){
                    result.append(",");
                }
            }
        }
        result.append("]}}");
        return result.toString();
    }

    @RequestMapping("/heatMap")
    @ResponseBody
    public String heatMap(){
        SoapClient soapClient = new SoapClient("heatMap",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        Map<String, String> patameterMap = new HashMap<String, String>(2);
        patameterMap.put("VC_USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");

        String soapRequestData = soapClient.buildRequestData(patameterMap);
        //System.out.println(soapRequestData);
        try {
            //String soapResponseData = soapClient.invoke(patameterMap);
            //result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        result.append("{\"result\":{\"object\":[");
        for(int i=0; i<2; i++){
            int indexSource = (int)(Math.random()*4);
            CityData destination = this.transportDataDestination.get(indexSource);
            result.append("{"
                    +"\"cityName\":" + "\"" + destination.cityName + "\""
                    +",\"coordinate\":" + "" + destination.coordinate + ""
                    +",\"deviceCount\":" + "\"" + destination.deviceCount + "\""
                    +"}"
            );
            result.append(",");
        }
        for(int j=0; j<30; j++){
            int indexSource = (int)(Math.random()*40);
            CityData source = this.transportDataSource.get(indexSource);
            result.append("{"
                    +"\"cityName\":" + "\"" + source.cityName + "\""
                    +",\"coordinate\":" + "" + source.coordinate + ""
                    +",\"deviceCount\":" + "\"" + source.deviceCount + "\""
                +"}"
            );
            if(j!=29){
                result.append(",");
            }
        }
        result.append("]}}");
        return result.toString();
    }


    /**
     * 设备数量每日趋势
     * @return
     */
    @RequestMapping("/commonSgnlCntByDay")
    @ResponseBody
    public String commonSgnlCntByDay(){
        /*SoapClient soapClient = new SoapClient("commonSgnlCntByDay",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        Map<String, String> patameterMap = new HashMap<String, String>(2);
        patameterMap.put("USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");
        patameterMap.put("START", "20171007");
        patameterMap.put("END", "20171027");
        patameterMap.put("DEVICE_ID", "ALL");

        String soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        String result = null;
        try {
            String soapResponseData = soapClient.invoke(patameterMap);
            result = XmlUtil.xml2JSON(soapResponseData.getBytes()).toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;*/
        return "{\"result\":{\"object\":[{\"DATE_CD\":\"20171012\",\"SGNL_CNT\":\"158\"},{\"DATE_CD\":\"20171013\",\"SGNL_CNT\":\"3934\"},{\"DATE_CD\":\"20171014\",\"SGNL_CNT\":\"13775\"},{\"DATE_CD\":\"20171015\",\"SGNL_CNT\":\"13895\"},{\"DATE_CD\":\"20171016\",\"SGNL_CNT\":\"13731\"},{\"DATE_CD\":\"20171017\",\"SGNL_CNT\":\"13506\"},{\"DATE_CD\":\"20171018\",\"SGNL_CNT\":\"57802\"},{\"DATE_CD\":\"20171020\",\"SGNL_CNT\":\"14\"},{\"DATE_CD\":\"20171023\",\"SGNL_CNT\":\"459\"},{\"DATE_CD\":\"20171024\",\"SGNL_CNT\":\"7303\"},{\"DATE_CD\":\"20171025\",\"SGNL_CNT\":\"86376\"},{\"DATE_CD\":\"20171026\",\"SGNL_CNT\":\"38977\"},{\"DATE_CD\":\"20171027\",\"SGNL_CNT\":\"186515\"}]}}";
    }
    @RequestMapping("/commonAblitiesCntByDay")
    @ResponseBody
    public String commonAblitiesCntByDay(){
        return "{\"result\":{\"object\":[{\"DATE_CD\":\"20171015\",\"SGNL_CNT\":\"10000\"},{\"DATE_CD\":\"20171016\",\"SGNL_CNT\":\"13731\"},{\"DATE_CD\":\"20171017\",\"SGNL_CNT\":\"13506\"},{\"DATE_CD\":\"20171018\",\"SGNL_CNT\":\"28652\"},{\"DATE_CD\":\"20171020\",\"SGNL_CNT\":\"11000\"},{\"DATE_CD\":\"20171023\",\"SGNL_CNT\":\"8548\"},{\"DATE_CD\":\"20171024\",\"SGNL_CNT\":\"7303\"},{\"DATE_CD\":\"20171025\",\"SGNL_CNT\":\"8376\"},{\"DATE_CD\":\"20171026\",\"SGNL_CNT\":\"8977\"},{\"DATE_CD\":\"20171027\",\"SGNL_CNT\":\"16515\"}]}}";
    }
    @RequestMapping("/commonAppRankCntByDay")
    @ResponseBody
    public String commonAppRankCntByDay(){
        return "{\"result\":{\"object\":[{\"VC_APP_NAME\":\"售电\",\"INT_CALL_CNT\":\"10000\"},{\"VC_APP_NAME\":\"脚手架\",\"INT_CALL_CNT\":\"13731\"},{\"VC_APP_NAME\":\"LNG点供\",\"INT_CALL_CNT\":\"13506\"},{\"VC_APP_NAME\":\"雪亮工程\",\"INT_CALL_CNT\":\"28652\"},{\"VC_APP_NAME\":\"智能家居1\",\"INT_CALL_CNT\":\"18652\"}]}}";
    }

}