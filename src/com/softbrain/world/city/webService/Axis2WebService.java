package com.softbrain.world.city.webService;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.ksoap2.SoapFault;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Java 通过 Axis2 调用 WebService <br>
 * 有三种方式:
 * <P>
 * 1.通过 wsdl2java.bat    调用 WebService <br>
 * 2.通过 ServiceClient    调用 WebService <br>
 * 3.通过 RPCServiceClient 调用 WebService <br>
 * </P>
 * Created by Administrator on 2016/12/21.
 */
public class Axis2WebService {
    public static void main(String[] args) throws SoapFault, AxisFault {
        // 方法一暂时不写 demo
        // TODO

        // 使用 ServiceClient 调用 WebService
        serviceClient();

        // 使用 RPCServiceClient 调用 WebService
        RPCServise();
    }

    /**
     * 使用 ServiceClient 调用 WebService
     */
    private static void serviceClient() throws AxisFault {
        // 使用 ServiceClient 调用 WebService
        ServiceClient sender = new ServiceClient();
        Options options = new Options();
        options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        // SOAP Action (通常是命名空间 + 调用的方法名称组成的 一段 URL)
        options.setAction("http://WebXml.com.cn/getRegionProvince");
        // 指定调用 WebService 的 URL (WebService 地址,通常是 WSDL 地址末尾的 "?wsdl" 去除后剩余的部分)
        EndpointReference targetEPR = new EndpointReference(
                "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx");
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setTo(targetEPR);
        sender.setOptions(options);
        OMFactory fac = OMAbstractFactory.getOMFactory();
        // 指定 WSDL 文件的命名空间
        OMNamespace omNs = fac.createOMNamespace("http://WebXml.com.cn/", "");
        // 指定要调用的方法
        OMElement data = fac.createOMElement("getRegionProvince", omNs);
        // 设置参数
//        OMElement theCityCode = fac.createOMElement("theCityCode ", omNs);
//        OMElement theUserID = fac.createOMElement("theUserID ", omNs);
//        theCityCode.setText("北京");
//        theUserID.setText("");
//        data.addChild(theCityCode);
//        data.addChild(theUserID);

        OMElement result = sender.sendReceive(data);
        // 输出调用 WebService 后服务器返回的结果
        System.out.println(result);

        // 获取返回结果中名为 getRegionProvinceResult 的结果字符串
        Iterator in = result.getChildrenWithLocalName("getRegionProvinceResult");
        // 循环遍历并输出结果
        while(in.hasNext()){
            OMElement om = (OMElement)in.next();
            Iterator in2 = om.getChildElements();
            while(in2.hasNext()){
                System.out.println(in2.next().toString());
                //System.out.println(((OMElement)in2.next()).getText());
            }
        }
    }

    /**
     * 使用 RPCServiceClient 调用 WebService
     */
    private static void RPCServise() throws AxisFault {
        // 使用 RPC 方式调用 WebService
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = serviceClient.getOptions();
        // SOAP Action (通常是命名空间 + 调用的方法名称组成的 一段 URL)
        options.setAction("http://WebXml.com.cn/getRegionProvince");
        // 指定调用 WebService 的 URL (WebService 地址,通常是 WSDL 地址末尾的 "?wsdl" 去除后剩余的部分)
        EndpointReference targetEPR = new EndpointReference(
                "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx");
        options.setTo(targetEPR);
        //  指定方法的参数值
        Object[] opAddEntryArgs = new Object[] {};
        //  指定方法返回值的数据类型的 Class 对象
        Class[] classes = new Class[] {Object.class};
        //  指定 WSDL 文件的命名空间及要调用的方法
        QName opAddEntry = new QName("http://WebXml.com.cn/", "getRegionProvince");
        //  调用方法并输出该方法的返回值 (参数二不能为 null,用 new Object[]{} 代替 null)
        System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs));
        /*Object[] result=serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
	    for (Object object : result) {
	    	System.out.println(object.toString());
	    }*/
    }
}
