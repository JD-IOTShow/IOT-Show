package com.jdnw.iotshow.util;

import com.alibaba.fastjson.JSONObject;
import com.scinfo.aep.sdk.general.impl.GeneralRequestImpl;
import com.scinfo.entity.AppHeader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoapClient {
    private String methodName;
    private String wsdlLocation;

    public SoapClient(String methodName, String wsdlLocation) {
        this.methodName = methodName;
        this.wsdlLocation = wsdlLocation;
    }

    public String invoke(Map<String, String> patameterMap) throws Exception {
        HttpPost httpPost= new HttpPost(wsdlLocation);
        String soapRequestData = buildRequestData(patameterMap);

        byte[] bytes = soapRequestData.getBytes("utf-8");
        InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);
        ContentType contentType = ContentType.create("application/soap+xml","utf-8");
        HttpEntity requestEntity = new InputStreamEntity(inputStream,bytes.length, contentType);
        httpPost.setEntity(requestEntity);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return  responseBody;
        } finally {
            httpclient.close();
        }

    }

    public String buildRequestData(Map<String, String> patameterMap) {
        StringBuffer soapRequestData = new StringBuffer();
        soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        soapRequestData.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\""
                        + " xmlns:dat=\"http://ws.wso2.org/dataservice\">\n");
        soapRequestData.append("\t<soap:Header/>\n");
        soapRequestData.append("\t<soap:Body>\n");
        soapRequestData.append("\t\t<dat:" + methodName +">\n");

        Set<String> nameSet = patameterMap.keySet();
        for (String name : nameSet) {
            soapRequestData.append("\t\t\t<dat:" + name + ">" + patameterMap.get(name)
                    + "</dat:" + name + ">\n");
        }

        soapRequestData.append("\t\t</dat:" + methodName + ">\n");
        soapRequestData.append("\t</soap:Body>\n");
        soapRequestData.append("</soap:Envelope>");

        return soapRequestData.toString();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //通用访问服务引擎的方法
        GeneralRequestImpl gr=new GeneralRequestImpl();
        //服务引擎鉴权参数
        AppHeader ah=new AppHeader();
        ah.setAppId("122"); // 应用ID
        ah.setAppKey("1715vK65u6d3aQ8"); //应用KEY
        ah.setAbilityCode("commonCitySgnlCntByDay");//能力code
        Map<String, String> map=new HashMap<String, String>();
        //查询参数
        //map.put("VC_USER_ID","ALL");
        map.put("USER_ID","ALL");
        map.put("TENANT_ID","ALL");
        map.put("DATE_CD","20171030");
        map.put("DEVICE_ID","ALL");
        map.put("PROVINCE_ID","ALL");
        String xml=gr.sendSoapReq(ah, map);
        System.out.println(xml);
        System.out.println("***************************************************************************************************");
        JSONObject json = XmlUtil.xml2JSON(xml.getBytes());
        System.out.println(json.toJSONString());
        String value = json.getJSONObject("result").getJSONArray("object").getJSONObject(0).getString("ABILITY_CNT");
        System.out.println(value);

//        SoapClient soapClient = new SoapClient("commonDeviceCityCnt",
//                "http://10.3.6.40:9773/services/dw_admin?wsdl");
//        Map<String, String> patameterMap = new HashMap<String, String>();
//        patameterMap.put("USER_ID", "ALL");
//        patameterMap.put("TENANT_ID", "ALL");
//        patameterMap.put("PROVINCE_ID", "ALL");
//
//        String soapRequestData = soapClient.buildRequestData(patameterMap);
//        System.out.println(soapRequestData);
//        String result = soapClient.invoke(patameterMap);
//        System.out.println(result);
//        JSONObject json = XmlUtil.xml2JSON(result.getBytes());
//        System.out.println(json.toJSONString());
    }

}