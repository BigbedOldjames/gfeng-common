package com.gckj.common.utils;

import com.wondersgroup.cuteinfo.client.exchangeserver.exchangetransport.dao.IMessageTransporterDAO;
import com.wondersgroup.cuteinfo.client.exchangeserver.exchangetransport.exception.UMessageTransportException;
import com.wondersgroup.cuteinfo.client.exchangeserver.exchangetransport.factory.ITransportClientFactory;
import com.wondersgroup.cuteinfo.client.exchangeserver.exchangetransport.impl.USendRequset;
import com.wondersgroup.cuteinfo.client.exchangeserver.exchangetransport.impl.USendResponse;
import com.wondersgroup.cuteinfo.client.exchangeserver.usersecurty.UserToken;
import com.wondersgroup.cuteinfo.client.util.UserTokenUtils;


public class LoginkUtils {

	//实际承运业户信息上传
	public static String LOGINK_CN_CREDIT_ENTERPRISE = "LOGINK_CN_CREDIT_ENTERPRISE";
	//人员信息上传
	public static String LOGINK_CN_CREDIT_PERSON = "LOGINK_CN_CREDIT_PERSON";
	//车辆信息上传
	public static String LOGINK_CN_CREDIT_VEHICLE = "LOGINK_CN_CREDIT_VEHICLE";
	//无车承运人委托单
	public static String JTWL_ENTRUST_TRANS_BookingNote = "JTWL_ENTRUST_TRANS_BookingNote";
	//无车承运人运单
	public static String LOGINK_CN_TRANSPORT_WAYBILL = "LOGINK_CN_TRANSPORT_WAYBILL";
	//无车承运人状态变化单
	public static String JTWL_ENTRUST_TRANS_BookingNoteStatus = "JTWL_ENTRUST_TRANS_BookingNoteStatus";
	//无车承运人回执单
	public static String ZJWL_LOGINK_HWGZ_SHIPMENTRETURN = "ZJWL_LOGINK_HWGZ_SHIPMENTRETURN";
	//资金流水单
	public static String LOGINK_CN_FREIGHTCHARGES = "LOGINK_CN_FREIGHTCHARGES";
	
	public static String send(String username,String password,String Actiontype,String xml){
		IMessageTransporterDAO transporter=null;
		USendResponse response = null;
		if(StringUtils.isNotNull(username) && StringUtils.isNotNull(password)){
			//调用统一认证的令牌认证服务
			/*UserToken token = UserTokenUtils.getTicket(username, password, UserConstants.getConfig("resourceId"), UserConstants.getConfig("authURL"));
			//设置目标地址，发送报文的内容
			USendRequset sendReq=new USendRequset();
			sendReq.setToaddress(new String[]{UserConstants.getConfig("toaddress")});
			//报文类型和具体报文xml
			sendReq.setSendRequestTypeXML(Actiontype, xml);
			//调用平台提供的发送服务发送报文
			transporter=ITransportClientFactory.createMessageTransporter(token, UserConstants.getConfig("targetURL"));
			response=transporter.send(sendReq);*/
		}
		if (response.isSendResult()) {
			return "send success";
		}else{
			return "send error";
		}
    }
}
