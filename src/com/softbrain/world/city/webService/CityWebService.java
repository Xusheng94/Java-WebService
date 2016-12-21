package com.softbrain.world.city.webService;

import com.softbrain.world.city.dao.CityDao;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * 通过 KSoap2 调用 WebService 获取全国城市
 * Created by Administrator on 2016/12/21.
 */
public class CityWebService {
    static CityDao cityDao;
    public static void main(String[] args) throws SoapFault {
        cityDao = new CityDao();
        getRegionProvince();
    }

    /**
     * 获得中国省份、直辖市、地区和与之对应的ID
     *
     * @throws SoapFault
     */
    private static void getRegionProvince() throws SoapFault {
        // 命名空间
        String nameSpace = "http://WebXml.com.cn/";
        // 调用的方法名称
        String methodName = "getRegionProvince";
        // EndPoint (WebService 地址,通常是 WSDL 地址末尾的 "?wsdl" 去除后剩余的部分)
        String endPoint = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";
        // SOAP Action (通常是命名空间 + 调用的方法名称组成的 一段 URL)
        String soapAction = "http://WebXml.com.cn/getRegionProvince";

        // 指定 WebService 的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用 WebService 接口需要传入的参数
        //rpc.addProperty("json", true);

        // 生成调用 WebService 方法的 SOAP 请求信息,并指定 SOAP 的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是 dotNet 开发的 WebService
        envelope.dotNet = true;
        // 等价于 envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用 WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // System.out.println("WebService Result:" + object.toString());
        // 获取返回的结果 (下面这行代码等价于 envelope.getResponse())
        String result = object.getProperty("getRegionProvinceResult").toString();
        //System.out.println("Result:" + result + "\n");

        // 开始截取字符串
        String s01 = result.substring(8, result.length() - 1);
        //System.out.println(s01 + "\n");
        // 移除 string=
        String s02 = s01.replace("string=", "");
        System.out.println(s02 + "\n");
        // 通过 ; 字符串截取为数组
        String[] ss01 = s02.split("; "); // 注意不要漏了那个空格
        if (ss01.length < 1) {
            System.out.println("没有更多城市了");
            return;
        }
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < ss01.length; i++) {
            String s03 = ss01[i];
            System.out.println(s03);
            // 通过 , 字符串截取为数组
            String[] ss02 = s03.split(",");
            System.out.println("\r" + ss02[0]);
            System.out.println("\r" + ss02[1]);
            System.out.println();
            if (i == (ss01.length - 1)) { // 到了数组尾部
                stringBuffer.append("('").append(ss02[1]).append("', '").append(ss02[0]).append("', '-1');");
                System.out.println(stringBuffer + "\n");
            } else {
                stringBuffer.append("('").append(ss02[1]).append("', '").append(ss02[0]).append("', '-1'), ");
            }
            // 查询该省份等下的城市并插入到数据库
            getSupportCityString(ss02[1]);
        }
        // 插入一个省份或直辖市或地区到数据库
        cityDao.insert(stringBuffer.toString());
    }

    /**
     * 获得支持的城市/地区名称和与之对应的ID
     *
     * @throws SoapFault
     */
    private static void getSupportCityString(String theRegionCode) {
        // 命名空间
        String nameSpace = "http://WebXml.com.cn/";
        // 调用的方法名称
        String methodName = "getSupportCityString";
        // EndPoint (WebService 地址,通常是 WSDL 地址末尾的 "?wsdl" 去除后剩余的部分)
        String endPoint = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";
        // SOAP Action (通常是命名空间 + 调用的方法名称组成的 一段 URL)
        String soapAction = "http://WebXml.com.cn/getSupportCityString";

        // 指定 WebService 的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用 WebService 接口需要传入的参数
        rpc.addProperty("theRegionCode", theRegionCode);

        // 生成调用 WebService 方法的 SOAP 请求信息,并指定 SOAP 的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是 dotNet 开发的 WebService
        envelope.dotNet = true;
        // 等价于 envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用 WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // System.out.println("WebService Result:" + object.toString());
        // 获取返回的结果 (下面这行代码等价于 envelope.getResponse())
        String result = object.getProperty("getSupportCityStringResult").toString();
        //System.out.println("Result:" + result + "\n");

        // 开始截取字符串
        String s01 = result.substring(8, result.length() - 1);
        //System.out.println(s01 + "\n");
        // 移除 string=
        String s02 = s01.replace("string=", "");
        System.out.println(s02 + "\n");
        // 通过 ; 字符串截取为数组
        String[] ss01 = s02.split("; "); // 注意不要漏了那个空格
        if (ss01.length < 1) {
            System.out.println("没有更多城市了");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < ss01.length) {
            String s03 = ss01[i];
            System.out.println(s03);
            // 通过 , 字符串截取为数组
            String[] ss02 = s03.split(",");
            System.out.println("\r" + ss02[0]);
            System.out.println("\r" + ss02[1]);
            System.out.println();
            if (i == (ss01.length - 1)) { // 到了数组尾部
                stringBuffer.append("('").append(ss02[1]).append("', '").append(ss02[0]).append("', ").append(theRegionCode).append(");");
                System.out.println(stringBuffer);
            } else {
                stringBuffer.append("('").append(ss02[1]).append("', '").append(ss02[0]).append("', ").append(theRegionCode).append("), ");
            }
            i++;
        }
        // 插入省份或直辖市或地区的所属的城市到数据库
        cityDao.insert(stringBuffer.toString());
    }
}