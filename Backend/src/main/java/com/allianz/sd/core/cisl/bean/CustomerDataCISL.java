package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class CustomerDataCISL extends ProcessData{
	
	private String name = null;
	
	private Date birthday = null; 
	
	private String gender = null;
	
	private String dataSource = null;
	
	private Integer partID = null;
	
	private String telType = null;
	
	private String tel = null;
	
	private Integer addID = null;
	
	private Integer contractID = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getPartID() {
		return partID;
	}

	public void setPartID(Integer partID) {
		this.partID = partID;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getTelType() {
		return telType;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getAddID() {
		return addID;
	}

	public void setAddID(Integer addID) {
		this.addID = addID;
	}

	public Integer getContractID() {
		return contractID;
	}

	public void setContractID(Integer contractID) {
		this.contractID = contractID;
	}
	
	

}
