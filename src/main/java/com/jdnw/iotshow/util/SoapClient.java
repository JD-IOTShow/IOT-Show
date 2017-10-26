package com.jdnw.iotshow.util;

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

    private String invoke(Map<String, String> patameterMap) throws Exception {
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

    private String buildRequestData(Map<String, String> patameterMap) {
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
        SoapClient soapClient = new SoapClient("commonDeviceCityCnt",
                "http://10.3.6.40:9773/services/dw_admin?wsdl");
        Map<String, String> patameterMap = new HashMap<String, String>();
        patameterMap.put("USER_ID", "ALL");
        patameterMap.put("TENANT_ID", "ALL");
        patameterMap.put("PROVINCE_ID", "ALL");

        String soapRequestData = soapClient.buildRequestData(patameterMap);
        System.out.println(soapRequestData);
        String result = soapClient.invoke(patameterMap);
        System.out.println(result);
    }

}