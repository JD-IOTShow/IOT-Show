package com.jdnw.iotshow.util;

import com.alibaba.fastjson.JSONObject;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class XmlUtil {
    public static JSONObject xml2JSON(byte[] xml) throws JDOMException, IOException {
        JSONObject json = new JSONObject();
        InputStream is = new ByteArrayInputStream(xml);
        SAXBuilder sb = new SAXBuilder();
        org.jdom2.Document doc = sb.build(is);
        Element root = doc.getRootElement().getChildren().get(0).getChildren().get(0);
        json.put("result", iterateElement(root));
        return json;
    }

    private static JSONObject iterateElement(Element element) {
        List node = element.getChildren();
        Element et = null;
        JSONObject obj = new JSONObject();
        List list = null;
        for (int i = 0; i < node.size(); i++) {
            list = new LinkedList();
            et = (Element) node.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0) {
                    continue;
                }
                if (obj.containsKey(et.getName().equalsIgnoreCase("Entry")?"object":et.getName())) {
                    list = (List) obj.get(et.getName().equalsIgnoreCase("Entry")?"object":et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName().equalsIgnoreCase("Entry")?"object":et.getName(), list);
            } else {
                if (obj.containsKey(et.getName().equalsIgnoreCase("Entry")?"object":et.getName())) {
                    list = (List) obj.get(et.getName().equalsIgnoreCase("Entry")?"object":et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName().equalsIgnoreCase("Entry")?"object":et.getName(), list.get(0));
            }
        }
        return obj;
    }

    public static void main(String[] args) throws JDOMException, IOException {
        String xml = "<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "   <soapenv:Body>\n" +
                "      <Entries xmlns=\"http://ws.wso2.org/dataservice\">\n" +
                "         <Entry>\n" +
                "            <CITY_ID>110100</CITY_ID>\n" +
                "            <CITY_NAM>北京市</CITY_NAM>\n" +
                "            <DEVICE_CNT>4</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>110000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>北京市</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>120100</CITY_ID>\n" +
                "            <CITY_NAM>天津市</CITY_NAM>\n" +
                "            <DEVICE_CNT>1</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>120000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>天津市</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>130100</CITY_ID>\n" +
                "            <CITY_NAM>石家庄市</CITY_NAM>\n" +
                "            <DEVICE_CNT>1</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>130000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>河北省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440100</CITY_ID>\n" +
                "            <CITY_NAM>广州市</CITY_NAM>\n" +
                "            <DEVICE_CNT>209648</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440200</CITY_ID>\n" +
                "            <CITY_NAM>韶关市</CITY_NAM>\n" +
                "            <DEVICE_CNT>5</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440300</CITY_ID>\n" +
                "            <CITY_NAM>深圳市</CITY_NAM>\n" +
                "            <DEVICE_CNT>1579</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440400</CITY_ID>\n" +
                "            <CITY_NAM>珠海市</CITY_NAM>\n" +
                "            <DEVICE_CNT>30</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440500</CITY_ID>\n" +
                "            <CITY_NAM>汕头市</CITY_NAM>\n" +
                "            <DEVICE_CNT>3</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440600</CITY_ID>\n" +
                "            <CITY_NAM>佛山市</CITY_NAM>\n" +
                "            <DEVICE_CNT>21</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440700</CITY_ID>\n" +
                "            <CITY_NAM>江门市</CITY_NAM>\n" +
                "            <DEVICE_CNT>5</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440800</CITY_ID>\n" +
                "            <CITY_NAM>湛江市</CITY_NAM>\n" +
                "            <DEVICE_CNT>20</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>440900</CITY_ID>\n" +
                "            <CITY_NAM>茂名市</CITY_NAM>\n" +
                "            <DEVICE_CNT>3</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441200</CITY_ID>\n" +
                "            <CITY_NAM>肇庆市</CITY_NAM>\n" +
                "            <DEVICE_CNT>3</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441300</CITY_ID>\n" +
                "            <CITY_NAM>惠州市</CITY_NAM>\n" +
                "            <DEVICE_CNT>19</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441400</CITY_ID>\n" +
                "            <CITY_NAM>梅州市</CITY_NAM>\n" +
                "            <DEVICE_CNT>8</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441500</CITY_ID>\n" +
                "            <CITY_NAM>汕尾市</CITY_NAM>\n" +
                "            <DEVICE_CNT>5</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441600</CITY_ID>\n" +
                "            <CITY_NAM>河源市</CITY_NAM>\n" +
                "            <DEVICE_CNT>3</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441700</CITY_ID>\n" +
                "            <CITY_NAM>阳江市</CITY_NAM>\n" +
                "            <DEVICE_CNT>5</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441800</CITY_ID>\n" +
                "            <CITY_NAM>清远市</CITY_NAM>\n" +
                "            <DEVICE_CNT>3</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>441900</CITY_ID>\n" +
                "            <CITY_NAM>东莞市</CITY_NAM>\n" +
                "            <DEVICE_CNT>18</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>442000</CITY_ID>\n" +
                "            <CITY_NAM>中山市</CITY_NAM>\n" +
                "            <DEVICE_CNT>15</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>445100</CITY_ID>\n" +
                "            <CITY_NAM>潮州市</CITY_NAM>\n" +
                "            <DEVICE_CNT>3</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>445200</CITY_ID>\n" +
                "            <CITY_NAM>揭阳市</CITY_NAM>\n" +
                "            <DEVICE_CNT>5</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>445300</CITY_ID>\n" +
                "            <CITY_NAM>云浮市</CITY_NAM>\n" +
                "            <DEVICE_CNT>16</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>440000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广东省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>450100</CITY_ID>\n" +
                "            <CITY_NAM>南宁市</CITY_NAM>\n" +
                "            <DEVICE_CNT>1</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>450000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>广西壮族自治区</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>510100</CITY_ID>\n" +
                "            <CITY_NAM>成都市</CITY_NAM>\n" +
                "            <DEVICE_CNT>5</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>510000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>四川省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>710100</CITY_ID>\n" +
                "            <CITY_NAM>台北市</CITY_NAM>\n" +
                "            <DEVICE_CNT>1</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>710000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>台湾省</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "         <Entry>\n" +
                "            <CITY_ID>810100</CITY_ID>\n" +
                "            <CITY_NAM>香港特别行政区</CITY_NAM>\n" +
                "            <DEVICE_CNT>5</DEVICE_CNT>\n" +
                "            <PROVINCE_ID>810000</PROVINCE_ID>\n" +
                "            <PROVINCE_NAM>香港特别行政区</PROVINCE_NAM>\n" +
                "         </Entry>\n" +
                "      </Entries>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

//        xml = "<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
//                "   <soapenv:Body>\n" +
//                "      <Entries xmlns=\"http://ws.wso2.org/dataservice\">\n" +
//                "         <Entry>\n" +
//                "            <ABILITY_CALL_CNT>1865971</ABILITY_CALL_CNT>\n" +
//                "         </Entry>\n" +
//                "      </Entries>\n" +
//                "   </soapenv:Body>\n" +
//                "</soapenv:Envelope>";
        JSONObject json = xml2JSON(xml.getBytes());
        System.out.println(json.toJSONString());
    }
}