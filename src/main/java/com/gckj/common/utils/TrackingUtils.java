package com.omysoft.common.utils;
/*package com.olmysoft.system.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.olmysoft.common.utils.ApplicationContextUtils;
import com.olmysoft.system.entity.SysOrganization;
import com.olmysoft.system.entity.SysUser;
import com.olmysoft.system.enums.DataReadState;
import com.olmysoft.system.service.OrganizationService;
import com.olmysoft.system.service.UserService;
import com.olmysoft.tms.entity.TmsBarge;
import com.olmysoft.tms.entity.TmsBargeDtl;
import com.olmysoft.tms.entity.;
import com.olmysoft.tms.entity.TmsDispatch;
import com.olmysoft.tms.entity.TmsDispatchDtl;
import com.olmysoft.tms.entity.TmsLoadingList;
import com.olmysoft.tms.entity.TmsLoadingListDtl;
import com.olmysoft.tms.entity.TmsNetwork;
import com.olmysoft.tms.entity.TmsOrder;
import com.olmysoft.tms.entity.TmsSign;
import com.olmysoft.tms.entity.TmsSource;
import com.olmysoft.tms.service.BargeDtlService;
import com.olmysoft.tms.service.BargeService;
import com.olmysoft.tms.service.DispatchDtlService;
import com.olmysoft.tms.service.DispatchService;
import com.olmysoft.tms.service.LoadingListDtlService;
import com.olmysoft.tms.service.LoadingListService;
import com.olmysoft.tms.service.NetworkService;
import com.olmysoft.tms.service.SignService;
import com.olmysoft.veh.entity.VehVehicle;
import com.olmysoft.veh.service.VehicleService;
import com.olmysoft.web.entity.WebOrder;
import com.olmysoft.web.entity.WebOrderAs;
import com.olmysoft.web.entity.WebOrderAsDtl;
import com.olmysoft.web.entity.WebOrderState;
import com.olmysoft.web.entity.WebSource;
import com.olmysoft.web.entity.WebSourceAs;
import com.olmysoft.web.entity.WebSourceAsDtl;
import com.olmysoft.web.enums.SourceState;
import com.olmysoft.web.enums.waybillTrackingContent;
import com.olmysoft.web.service.OrderAsDtlService;
import com.olmysoft.web.service.OrderAsService;
import com.olmysoft.web.service.OrderService;
import com.olmysoft.web.service.OrderStateService;
import com.olmysoft.web.service.SourceAsDtlService;
import com.olmysoft.web.service.SourceAsService;
import com.olmysoft.web.service.SourceService;


*//**
 * 运单跟踪工具类
 * syq
 *//*

public class TrackingUtils {
	
	
	private static OrganizationService organizationService = (OrganizationService)ApplicationContextUtils.getBean("organizationService");
	private static SourceService sourceService = (SourceService)ApplicationContextUtils.getBean("webSourceService");
	private static SourceAsService sourceAsService = (SourceAsService)ApplicationContextUtils.getBean("webSourceAsService");

	private static SourceAsDtlService sourceAsDtlService = (SourceAsDtlService)ApplicationContextUtils.getBean("WebSourceAsDtlService");
	private static OrderService orderService = (OrderService)ApplicationContextUtils.getBean("webOrderService");
	private static UserService userService = (UserService)ApplicationContextUtils.getBean("userService");
	private static VehicleService vehicleService = (VehicleService)ApplicationContextUtils.getBean("vehVehicleService");
	private static BargeService bargeService = (BargeService)ApplicationContextUtils.getBean("tmsBargeService");
	private static BargeDtlService bargeDtlService = (BargeDtlService)ApplicationContextUtils.getBean("tmsBargeDtlService");
	private static OrderAsService OrderAsService = (OrderAsService)ApplicationContextUtils.getBean("webOrderAsService");
	private static OrderAsDtlService orderAsDtlService = (OrderAsDtlService)ApplicationContextUtils.getBean("webOrderAsDtlService");
	private static LoadingListService loadingListService = (LoadingListService)ApplicationContextUtils.getBean("tmsLoadingListService");
	private static LoadingListDtlService loadingListDtlService = (LoadingListDtlService)ApplicationContextUtils.getBean("tmsLoadingListDtlService");
	private static NetworkService networkService = (NetworkService)ApplicationContextUtils.getBean("tmsNetworkService");
	private static DispatchService dispatchService = (DispatchService)ApplicationContextUtils.getBean("tmsDispatchService");
	private static DispatchDtlService dispatchDtlService = (DispatchDtlService)ApplicationContextUtils.getBean("tmsDispatchDtlService");
	private static SignService signService = (SignService)ApplicationContextUtils.getBean("tmsSignService");
	private static OrderStateService orderStateService = (OrderStateService)ApplicationContextUtils.getBean("tmsOrderState");
	public static void getTrackingContent(String type,Object object){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WebOrderState webOrderState = new WebOrderState();
		String TrackingContent = "";
		if("DDSC".equals(type)){
			WebSource webSource = (WebSource)object;
			TrackingContent = waybillTrackingContent.DDSC.replace("$time",sdf.format(webSource.getCreateTime())).replace("$id",webSource.getId().toString()).replace("$orderNum",webSource.getOrderNum());
			webOrderState.setOrderNum(webSource.getOrderNum());
			webOrderState.setState(SourceState.PENDING.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(webSource.getCompanyId());
			webOrderState.setContent(TrackingContent);
			
			saveOrderState(webOrderState);
		}
		if("YYYFP".equals(type)){
			WebSource webSource = (WebSource)object;
			WebSourceAsDtl webSourceAsDtl = sourceAsDtlService.getDtlByOrderNum(webSource.getOrderNum());
			WebSourceAs webSourceAs = sourceAsService.getById(webSourceAsDtl.getAsId());
			SysOrganization sysOrganization = organizationService.getById(webSourceAs.getAsCompanyId());
			TrackingContent = waybillTrackingContent.YYYFP.replace("$time",sdf.format(webSourceAs.getUpdateTime())).replace("$id", webSource.getId().toString()).replace("$orderNum", webSource.getOrderNum()).replace("$receiver",sysOrganization.getName());
			webOrderState.setOrderNum(webSource.getOrderNum());
			webOrderState.setState(SourceState.ASSIGNMENT.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(webSource.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("YYYJS".equals(type)){
			TmsSource tmsSource= (TmsSource)object;
			WebSource webSource = sourceService.getSourceByOrderNum(tmsSource.getOrderNum());
			SysUser sysUser = userService.getById(tmsSource.getUpdateById());
			TrackingContent = waybillTrackingContent.YYYJS.replace("$time",sdf.format(tmsSource.getUpdateTime())).replace("$id", webSource.getId().toString()).replace("$orderNum",tmsSource.getOrderNum()).replace("$receiver",sysUser.getUserName());
			webOrderState.setOrderNum(tmsSource.getOrderNum());
			webOrderState.setState(SourceState.ALREADYRECEIVE.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsSource.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("YYYDB".equals(type)){
			TmsSource tmsSource= (TmsSource)object;
			WebSource webSource = sourceService.getSourceByOrderNum(tmsSource.getOrderNum());
			TmsBargeDtl tmsBargeDtl = bargeDtlService.getTmsBargeDtlBySourceId(tmsSource.getId());
			TmsBarge tmsBarge = bargeService.getById(tmsBargeDtl.getBargeId());
			VehVehicle vehVehicle = vehicleService.getById(tmsBarge.getVehicleId());
			String departure = "";
			if(tmsBarge.getDepartureTime()!=null){
				departure=sdf.format(tmsBarge.getDepartureTime());
			}
			TrackingContent = waybillTrackingContent.YYYDB.replace("$time",sdf.format(tmsBarge.getCreateTime())).replace("$id", webSource.getId().toString()).replace("$orderNum",tmsSource.getOrderNum()).replace("$bargeNum",tmsBarge.getOrderNum()).replace("$departure",departure).replace("$vehicle",vehVehicle.getVehicleLicenceNo()).replace("$driver",tmsBarge.getDriver()).replace("$tel",tmsBarge.getTel());
			webOrderState.setOrderNum(tmsSource.getOrderNum());
			webOrderState.setState(SourceState.BAR112GE.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsSource.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("YDD".equals(type)){
			TmsSource tmsSource= (TmsSource)object;
			TmsBargeDtl tmsBargeDtl = bargeDtlService.getTmsBargeDtlBySourceId(tmsSource.getId());
			TmsBarge tmsBarge = bargeService.getById(tmsBargeDtl.getBargeId());
			TrackingContent = waybillTrackingContent.YDD.replace("$time",sdf.format(tmsBarge.getUpdateTime())).replace("$driver",tmsBarge.getDriver());
			webOrderState.setOrderNum(tmsSource.getOrderNum());
			webOrderState.setState(SourceState.BARGE.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsSource.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("SCWLD".equals(type)){
			WebOrder webOrder = (WebOrder)object;
			SysUser sysUser = userService.getById(webOrder.getCreateById());
			TrackingContent = waybillTrackingContent.SCWLD.replace("$time",sdf.format(webOrder.getCreateTime())).replace("$orderNum",webOrder.getSourceNum()).replace("$wldh",webOrder.getOrderNum()).replace("$create",sysUser.getUserName());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.BOOKPENDING.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(webOrder.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("YSH".equals(type)){
			WebOrder webOrder = (WebOrder)object;
			SysUser sysUser = userService.getById(webOrder.getUpdateById());
			TrackingContent = waybillTrackingContent.YSH.replace("$time",sdf.format(webOrder.getUpdateTime())).replace("$orderNum",webOrder.getOrderNum()).replace("$shr",sysUser.getUserName());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.EXAMINE.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(webOrder.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("WLYFP".equals(type)){
			WebOrder webOrder = (WebOrder)object;
			WebOrderAsDtl webOrderAsDtl = orderAsDtlService.getDtlByOrderNum(webOrder.getOrderNum());
			WebOrderAs webOrderAs = OrderAsService.getById(webOrderAsDtl.getAsId());
			SysOrganization sysOrganization = organizationService.getById(webOrderAs.getAsCompanyId());
			TrackingContent = waybillTrackingContent.WLYFP.replace("$time",sdf.format(webOrderAs.getCreateTime())).replace("$waybillNum",webOrder.getOrderNum()).replace("$asCompanyName",sysOrganization.getName());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.APP.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(webOrder.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("WLYJS".equals(type)){
			TmsOrder tmsOrder = (TmsOrder)object;
			WebOrder webOrder = orderService.getOrderByOrderNum(tmsOrder.getOrderNum());
			SysUser sysUserKD = userService.getById(tmsOrder.getCreateById());
			SysUser sysUser = userService.getById(tmsOrder.getUpdateById());
			TrackingContent = waybillTrackingContent.WLYJS.replace("$time",sdf.format(tmsOrder.getUpdateTime())).replace("$waybillNum",tmsOrder.getOrderNum()).replace("$receiver",sysUser.getUserName()).replace("$orderNum",tmsOrder.getOrderNum()).replace("$kaidanren",sysUserKD.getUserName());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.DELIVERY.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsOrder.getCompanyId());
			webOrderState.setContent(TrackingContent);
			saveOrderState(webOrderState);
		}
		if("TYYZC".equals(type)){
			TmsBookingNote tmsBookingNote = (TmsBookingNote)object;
			WebOrder webOrder = orderService.getOrderByOrderNum(tmsBookingNote.getWaybillNum());
			TrackingContent = waybillTrackingContent.TYYZC.replace("$time",sdf.format(tmsBookingNote.getCreateTime())).replace("$orderNum",tmsBookingNote.getOrderNum());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.LD.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsBookingNote.getCompanyId());
			webOrderState.setContent(TrackingContent);
			webOrderState.setReadState(DataReadState.PLATFORM.getValue());
			saveOrderState(webOrderState);
		}
		if("TYYFC".equals(type)){
			TmsBookingNote tmsBookingNote = (TmsBookingNote)object;
			WebOrder webOrder = orderService.getOrderByOrderNum(tmsBookingNote.getWaybillNum());
			TmsLoadingListDtl tmsLoadingListDtl = loadingListDtlService.getTmsLoadingListDtlByBookingId(tmsBookingNote.getId());
			TmsLoadingList tmsLoadingList = loadingListService.getById(tmsLoadingListDtl.getLoadingListId());
			TmsNetwork tmsNetwork = networkService.getById(tmsLoadingList.getDestinationId());
			VehVehicle vehVehicle = vehicleService.getById(tmsLoadingList.getVehicleId());
			TrackingContent = waybillTrackingContent.TYYFC.replace("$time",sdf.format(tmsLoadingList.getUpdateTime())).replace("$orderNum",tmsBookingNote.getOrderNum()).replace("$loadingListNum",tmsLoadingList.getOrderNum()).replace("$destinationName",tmsNetwork.getName()).replace("$vehicleLicenceNo",vehVehicle.getVehicleLicenceNo()).replace("$driver",tmsLoadingList.getDriver()).replace("$tel",tmsLoadingList.getTel());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.LDSD.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsBookingNote.getCompanyId());
			webOrderState.setContent(TrackingContent);
			webOrderState.setReadState(DataReadState.PLATFORM.getValue());
			saveOrderState(webOrderState);
		}
		if("TYYDD".equals(type)){
			TmsBookingNote tmsBookingNote = (TmsBookingNote)object;
			WebOrder webOrder = orderService.getOrderByOrderNum(tmsBookingNote.getWaybillNum());
			TmsLoadingListDtl tmsLoadingListDtl = loadingListDtlService.getTmsLoadingListDtlByBookingId(tmsBookingNote.getId());
			TmsLoadingList tmsLoadingList = loadingListService.getById(tmsLoadingListDtl.getLoadingListId());
			TmsNetwork tmsNetwork = networkService.getById(tmsLoadingList.getDestinationId());
			TrackingContent = waybillTrackingContent.TYYDD.replace("$time",sdf.format(tmsBookingNote.getUpdateTime())).replace("$orderNum",tmsBookingNote.getOrderNum()).replace("$destinationName",tmsNetwork.getName()).replace("$receiverTel",tmsLoadingList.getTel());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.SDA.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsBookingNote.getCompanyId());
			webOrderState.setContent(TrackingContent);
			webOrderState.setReadState(DataReadState.PLATFORM.getValue());
			saveOrderState(webOrderState);
		}
		if("TYYXH".equals(type)){
			TmsBookingNote tmsBookingNote = (TmsBookingNote)object;
			WebOrder webOrder = orderService.getOrderByOrderNum(tmsBookingNote.getWaybillNum());
			TrackingContent = waybillTrackingContent.TYYXH.replace("$time",sdf.format(tmsBookingNote.getUpdateTime())).replace("$orderNum",tmsBookingNote.getOrderNum());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.SSDSSDA.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsBookingNote.getCompanyId());
			webOrderState.setContent(TrackingContent);
			webOrderState.setReadState(DataReadState.PLATFORM.getValue());
			saveOrderState(webOrderState);
		}
		if("TYYSH".equals(type)){
			TmsBookingNote tmsBookingNote = (TmsBookingNote)object;
			WebOrder webOrder = orderService.getOrderByOrderNum(tmsBookingNote.getWaybillNum());
			TmsDispatchDtl tmsDispatchDtl = dispatchDtlService.getTmsDispatchByBookingId(tmsBookingNote.getId());
			TmsDispatch tmsDispatch = dispatchService.getById(tmsDispatchDtl.getDispatchId());
			VehVehicle vehVehicle = vehicleService.getById(tmsDispatch.getVehicleId());
			TrackingContent = waybillTrackingContent.TYYSH.replace("$time",sdf.format(tmsDispatch.getCreateTime())).replace("$orderNum",tmsBookingNote.getOrderNum()).replace("$driver",tmsDispatch.getDriver()).replace("$tel",tmsDispatch.getTel()).replace("$vehicleLicenceNo",vehVehicle.getVehicleLicenceNo());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.SFDS.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsBookingNote.getCompanyId());
			webOrderState.setContent(TrackingContent);
			webOrderState.setReadState(DataReadState.PLATFORM.getValue());
			saveOrderState(webOrderState);
		}
		if("TYYQS".equals(type)){
			TmsBookingNote tmsBookingNote = (TmsBookingNote)object;
			WebOrder webOrder = orderService.getOrderByOrderNum(tmsBookingNote.getWaybillNum());
			TmsSign tmsSign = signService.getTmsSignByBookingNoteId(tmsBookingNote.getId());
			TrackingContent = waybillTrackingContent.TYYQS.replace("$time",sdf.format(tmsSign.getCreateTime())).replace("$orderNum",tmsBookingNote.getOrderNum()).replace("$sign",tmsSign.getReceiver()).replace("$identityCard",tmsSign.getIdentityCard()).replace("$tel",tmsBookingNote.getReceiverTel());
			webOrderState.setOrderNum(webOrder.getSourceNum());
			webOrderState.setState(SourceState.CONFIRMED.getValue());
			webOrderState.setCreateTime(new Date());
			webOrderState.setCompanyId(tmsBookingNote.getCompanyId());
			webOrderState.setContent(TrackingContent);
			webOrderState.setReadState(DataReadState.PLATFORM.getValue());
			saveOrderState(webOrderState);
		}
		return webOrderState;
	}
	private static void saveOrderState(WebOrderState webOrderState){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNum", webOrderState.getOrderNum());
		params.put("state", webOrderState.getState());
		List ls = orderStateService.findOrderStateInfo(params);
		if(ls.size()==0){
			 orderStateService.save(webOrderState);
		}
	}
}
*/