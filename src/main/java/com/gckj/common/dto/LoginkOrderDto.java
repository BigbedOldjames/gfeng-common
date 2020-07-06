package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


/**
 * 
 * @Description：无车承运人委托单DTO
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginkOrderDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8115809559438386885L;
	private Long id; //ID
	private String loginkState; //上报状态
	private String orderNum; //物流运单号
	private String consignorName; //发货方
	private String senderCode; //发货人代码
	private String sender; //发货人
	private String senderTel; //发货人电话
	private String shippingAddress; //发货地址
	private String consigneeName; //收货方
	private String receiver; //收货人
	private String receiverTel; //收货人电话
	private String receivingAddress; // 收货地址
	private String amount; //总费用
	private String departureCode; //起点行政区域代码
	private String departureName;	//起点行政区域名称
	private String destinationCode; //到点行政区域代码
	private String destinationName; //到点行政区域名称
	private String carrierName; //承运人名称
	private String carrierIdentifier; //承运人标识符
	private String carrierContactName; //承运人联系人名称
	private String carrierTelephoneNumber; //承运人电话号码
	private String deliveryDate;
	private String state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getAmount() {
		return amount;
	}
	public String getCarrierContactName() {
		return carrierContactName;
	}
	
	public String getCarrierIdentifier() {
		return carrierIdentifier;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getCarrierTelephoneNumber() {
		return carrierTelephoneNumber;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	
	
	public String getConsignorName() {
		return consignorName;
	}
	public String getDepartureCode() {
		return departureCode;
	}
	public String getDepartureName() {
		return departureName;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public Long getId() {
		return id;
	}
	public String getLoginkState() {
		return loginkState;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public String getReceiver() {
		return receiver;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public String getReceivingAddress() {
		return receivingAddress;
	}
	public String getSender() {
		return sender;
	}
	public String getSenderCode() {
		return senderCode;
	}
	public String getSenderTel() {
		return senderTel;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setCarrierContactName(String carrierContactName) {
		this.carrierContactName = carrierContactName;
	}
	public void setCarrierIdentifier(String carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setCarrierTelephoneNumber(String carrierTelephoneNumber) {
		this.carrierTelephoneNumber = carrierTelephoneNumber;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}
	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setLoginkState(String loginkState) {
		this.loginkState = loginkState;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
	public void setReceivingAddress(String receivingAddress) {
		this.receivingAddress = receivingAddress;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}
	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	

}
