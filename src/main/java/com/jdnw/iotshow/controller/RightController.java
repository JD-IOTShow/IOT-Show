package com.jdnw.iotshow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AUTHOR: zxy
 * com.jdnw.iotshow.controller
 * DATE: 2017/10/30
 * TIME: 15:43
 **/
@Controller
public class RightController {
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
