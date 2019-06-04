package org.ms.DmhThymeLeaf.entity;

import java.util.Date;
import java.util.Map;

public class CsuAdmin {
	
		
	private Map providerMap;
	private Map locationMap;
	private String locationCode;
	private String orgCode;
	private String orgName;
	private String bedType;
	private String bedName;
	private String modifyUser;
	private Date modifyDate;

	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public String getBedName() {
		return bedName;
	}
	public void setBedName(String bedName) {
		this.bedName = bedName;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Map getProviderMap() {
		return providerMap;
	}
	public void setProviderMap(Map providerMap) {
		this.providerMap = providerMap;
	}
	public Map getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(Map locationMap) {
		this.locationMap = locationMap;
	}
	public CsuAdmin() {
		super();
	}
	
	
}
