package org.ms.DmhThymeLeaf.entity;


import org.hibernate.validator.constraints.Length;
import org.ms.DmhThymeLeaf.enums.DMHRoles;
import org.slf4j.Logger;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DmhUser implements Serializable{
	
	//private static final Logger logger =  Logger.(DmhUser.class);
	
	@Size(min=1, max=32, message="First name must be between 1 and 32 characters")
	public String who;
	@Size(min=1, max=32, message="First name must be between 1 and 32 characters")
	public String first_name;
	@Size(min=1, max=32, message="First name must be between 1 and 32 characters")
	public String last_name;
	@NotNull
	@Size(min=2, max=3, message="Location code must be min 2 and max 23")
	public String location_code;
	@NotNull(message="permit cannot be null")
	public String permit;
	@NotNull(message="password cannot be null")
	public String pswd;
	public boolean occupancRate;
	private DMHRoles role;
	@NotNull(message="email cannot be null")
	@Email(message="enter a valid email")
	public String email;
	@NotNull
	@Length(min=10, max=12, message="Enter 10 didgit phone number")
	public String phone;
	private String csu;
	private String cl;
	private String idd;
	
	
	public DmhUser(@Size(min = 1, max = 32, message = "First name must be between 1 and 32 characters") String who,
			@Size(min = 1, max = 32, message = "First name must be between 1 and 32 characters") String first_name,
			@Size(min = 1, max = 32, message = "First name must be between 1 and 32 characters") String last_name,
			@NotNull(message = "permit cannot be null") String permit,
			@NotNull(message = "password cannot be null") String pswd, DMHRoles role) {
		super();
		this.who = who;
		this.first_name = first_name;
		this.last_name = last_name;
		this.permit = permit;
		this.pswd = pswd;
		this.role = role;
	}
	public DmhUser() {
		super();
	}
	
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getLocation_code() {
		return location_code;
	}
	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}
	public String getPermit() {
		return permit;
	}
	public void setPermit(String permit) {
		this.permit = permit;
	}
	/*public String getLAST_LOGGED_IN() {
		return LAST_LOGGED_IN;
	}
	public void setLAST_LOGGED_IN(String lAST_LOGGED_IN) {
		LAST_LOGGED_IN = lAST_LOGGED_IN;
	}*/
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public DMHRoles getRole() {
		return role;
	}
	public void setRole(DMHRoles role) {
		this.role = role;
	}
	public boolean isOccupancRate() {
		return occupancRate;
	}
	public void setOccupancRate(boolean occupancRate) {
		this.occupancRate = occupancRate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCsu() {
		return csu;
	}
	public void setCsu(String csu) {
		this.csu = csu;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getIdd() {
		return idd;
	}
	public void setIdd(String idd) {
		this.idd = idd;
	}
	
	
	

}
