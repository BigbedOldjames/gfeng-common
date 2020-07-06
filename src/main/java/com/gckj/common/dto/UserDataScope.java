package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * @Description：数据权限DTO（可扩展字段）
 * @author：ldc
 * date：2020-06-23
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDataScope implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -650911923392893509L;
	
	//-------------基本信息-----------------
	private Long userId; // 用户ID
	private String userName; // 用户名称
	private String realName; // 真实名称
	private Long companyId; // 所属公司ID
	private String companyName;	// 所属公司名称
	private String companyInnerPhone;//企业内线电话
	private String companyCode; // 公司代码
	private String companyType;	//公司类型
	private String exchangeCode;	//物流交换代码
	private String companyParentIds;
	private Long departmentId; // 所属部门ID
	//--------------总公司----------------------
	private Long headquarterId; //总公司ID
	private String headquarterParentIds; //总公司parentIds
	//--------------数据权限-------------------
	private List<Long> grantCompanyIds; // 授权的公司ID
	private List<Long> grantNoDepartCompanyIds; // 授权的无部门的公司
	private List<Long> grantDepartmentIds; // 授权的// 部门ID
	private String dataScopeSql; // 数据权限sql
	private String dataCompanyIdsSql;
	//--------------其它-------------------
	private Map<String, Object> customData; //用户自定义数据
	private String userType;//用户类型.
	//用户认证状态
	private String userAuthState;
	//司机保证金
	private Double deposit;

	/**
	 * 是否获取全部数据
	 */
	private String isWholeData;

	/**
	 * 数据来源
	 */
	private String origin;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getIsWholeData() {
		return isWholeData;
	}

	public void setIsWholeData(String isWholeData) {
		this.isWholeData = isWholeData;
	}

	public String getUserAuthState() {
		return userAuthState;
	}

	public void setUserAuthState(String userAuthState) {
		this.userAuthState = userAuthState;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCompanyCode() {
		return companyCode;
	}
	public Long getCompanyId() {
		return companyId;
	}
	
	public String getCompanyInnerPhone() {
		return companyInnerPhone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanyParentIds() {
		return companyParentIds;
	}
	public String getCompanyType() {
		return companyType;
	}
	public Map<String, Object> getCustomData() {
		return customData;
	}
	
	/*public String getDataCompanyIdsSql() {
		String companyIds = "";
		for (Long l : grantCompanyIds) {
			companyIds += "," + l;
		}
		companyIds = companyIds.substring(1, companyIds.length());
		dataCompanyIdsSql = " and t.company_id in (" + companyIds + ") ";
		return dataCompanyIdsSql;
	}*/
	
	//------------------对外接口---------------
	/**
	 * 排除前缀“ and” 
	 * eg: and t.company_id = ?  -----> t.company_id = ?
	 */
	/*public String getDataScopeExcludeAndSql(){
		return dataScopeSql.substring(" and".length(), dataScopeSql.length());
	}*/
	public String getDataScopeSql() {
		return dataScopeSql;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public String getExchangeCode() {
		return exchangeCode;
	}
	
	
	public List<Long> getGrantCompanyIds() {
		return grantCompanyIds;
	}
	public List<Long> getGrantDepartmentIds() {
		return grantDepartmentIds;
	}
	
	public List<Long> getGrantNoDepartCompanyIds() {
		return grantNoDepartCompanyIds;
	}
	public Long getHeadquarterId() {
		return headquarterId;
	}

	public String getHeadquarterParentIds() {
		return headquarterParentIds;
	}

	public String getRealName() {
		return realName;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setCompanyInnerPhone(String companyInnerPhone) {
		this.companyInnerPhone = companyInnerPhone;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCompanyParentIds(String companyParentIds) {
		this.companyParentIds = companyParentIds;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public void setCustomData(Map<String, Object> customData) {
		this.customData = customData;
	}

	public void setDataScopeSql(String dataScopeSql) {
		this.dataScopeSql = dataScopeSql;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public void setGrantCompanyIds(List<Long> grantCompanyIds) {
		this.grantCompanyIds = grantCompanyIds;
	}

	public void setGrantDepartmentIds(List<Long> grantDepartmentIds) {
		this.grantDepartmentIds = grantDepartmentIds;
	}

	public void setGrantNoDepartCompanyIds(List<Long> grantNoDepartCompanyIds) {
		this.grantNoDepartCompanyIds = grantNoDepartCompanyIds;
	}

	public void setHeadquarterId(Long headquarterId) {
		this.headquarterId = headquarterId;
	}

	public void setHeadquarterParentIds(String headquarterParentIds) {
		this.headquarterParentIds = headquarterParentIds;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
