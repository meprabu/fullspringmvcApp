package org.ms.DmhThymeLeaf;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

import org.ms.DmhThymeLeaf.entity.CurrentUser;
import org.ms.DmhThymeLeaf.entity.DmhUser;
import org.ms.DmhThymeLeaf.enums.DMHRoles;
import org.springframework.context.annotation.Bean;

public class UserDetailsServiceMock {
	
	
	 	/*@Bean
	    @Primary
	    public UserDetailsService userDetailsService() {
	        
	        DmhUser dmhUser = new DmhUser();
	        dmhUser.setWho("dmh.prabu.jayapandia");
	        dmhUser.setPswd("test123");
	        dmhUser.setRole(DMHRoles.USER);
	        
	        CurrentUser cu = new CurrentUser(dmhUser);
	        
	        
	        User managerUser = new UserImpl("Manager User", "manager@company.com", "password");
	        UserActive managerActiveUser = new UserActive(managerUser, Arrays.asList(
	                new SimpleGrantedAuthority("ROLE_MANAGER"),
	                new SimpleGrantedAuthority("PERM_FOO_READ"),
	                new SimpleGrantedAuthority("PERM_FOO_WRITE"),
	                new SimpleGrantedAuthority("PERM_FOO_MANAGE")
	        ));

	        return new InMemoryUserDetailsManager(Arrays.asList(cu));
	    }*/

}
