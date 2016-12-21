package com.softbrain.world.city.webService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Android 通过 KSoap2 调用 WebService
 * Created by Administrator on 2016/12/21.
 */
public class KSoap2WebService {
    public static void main(String[] args) throws SoapFault {
        webService();
    }

    private static void webService() throws SoapFault {
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
        rpc.addProperty("json", true);

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
        System.out.println("WebService Result:" + object.toString());
        // 获取返回的结果 (下面这行代码等价于 envelope.getResponse())
        String result = object.getProperty("getRegionProvinceResult").toString();
        System.out.println("Result:" + result);
    }
}
