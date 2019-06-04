package org.ms.DmhThymeLeaf.entity;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User{
	
	


	private DmhUser dmhuser;
	private String csu;
	private String cl;
	private String idd;
	private String org;
	private String firstName;
	private String lastname;
	private String location;
	
	public CurrentUser(DmhUser user) {
		super(user.getWho(), user.getPswd(), AuthorityUtils.createAuthorityList("ROLE_"+user.getRole().toString()));
		this.dmhuser = user;
		this.firstName = user.getFirst_name();
		this.lastname = user.getLast_name();
		this.csu = user.getCsu();
		this.cl = user.getCl();
		this.idd = user.getIdd();
		this.org = user.getPermit();
		this.location = user.getLocation_code();
	}
	
	
	
	public DmhUser getDmhuser() {
		return dmhuser;
	}

	public String getCsu() {
		return csu;
	}

	public String getCl() {
		return cl;
	}

	public String getIdd() {
		return idd;
	}

	public String getOrg() {
		return org;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public String getLocation() {
		return location;
	}
	
	

}
