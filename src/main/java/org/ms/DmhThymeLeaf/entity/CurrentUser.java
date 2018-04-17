package org.ms.DmhThymeLeaf.entity;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User{
	
	
	private DmhUser dmhuser;
	
	public CurrentUser(DmhUser user) {
		super(user.getWho(), user.getPswd(), AuthorityUtils.createAuthorityList("ROLE_"+user.getRole().toString()));
		this.dmhuser = user;
	}
	
	public DmhUser getUser() {
        return dmhuser;
    }
	

}
