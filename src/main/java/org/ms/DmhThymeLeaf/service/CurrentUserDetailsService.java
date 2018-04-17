package org.ms.DmhThymeLeaf.service;

import org.ms.DmhThymeLeaf.Dao.UserServiceDao;
import org.ms.DmhThymeLeaf.entity.CurrentUser;
import org.ms.DmhThymeLeaf.entity.DmhUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService{

	@Autowired
	UserServiceDao userServiceDao;
	
	
	@Override
	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
		DmhUser user = userServiceDao.getUserById(username);
		CurrentUser currentUser =  new CurrentUser(user);
		return currentUser;
	}
	
	
	
	
}
