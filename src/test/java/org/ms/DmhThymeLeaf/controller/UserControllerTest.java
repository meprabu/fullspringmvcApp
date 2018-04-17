package org.ms.DmhThymeLeaf.controller;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ms.DmhThymeLeaf.DmhThymeLeafApplication;

import org.ms.DmhThymeLeaf.Dao.BcUserDAO;
import org.ms.DmhThymeLeaf.Dao.UserServiceDao;
import org.ms.DmhThymeLeaf.config.DmhSecurityConfig;
import org.ms.DmhThymeLeaf.entity.DmhUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	
	
	@MockBean
	private UserServiceDao userDao;
	
	@MockBean
	private BcUserDAO bcdao;
	
	@MockBean
	private DataSource dataSource;
	
	
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	//	wac.getServletContext().setAttribute(
	  //          WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		//this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	/*
	private MockHttpSession buildSession(Authentication authentication) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, new MockSecurityContext(authentication));
        return session;
    }
 
    *//**
     * @return a session for an employee with the id 500.
     *//*
    protected MockHttpSession user() {
        return user(500L);
    }
 
    protected MockHttpSession user(Long id) {
        return buildSession(AuthenticationMocks.userAuthentication(id));
    }
 */
    
	
	
	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "USER")
	public void testGotoUser() throws Exception{
		//user();
		this.mockMvc.perform(get("/user")).andExpect(view().name("user"));
			
	}
	
	@Test
	@WithMockUser(username = "user1", password = "pwd", roles = "USER")
	public void testGotoAddUser() throws Exception{
		//user();
		//this.mockMvc.perform(get("/use/AddUser")).andExpect(view().name("addUser"));
		this.mockMvc.perform(get("/user/Adduser"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("dmhUser"))
		.andExpect(view().name("addUser"));
			
	}
	
	
	//@Test
	@WithMockUser(username = "dmh.prabu.jayapandia", password = "pwd", roles = "ADMIN")
	public void testGotoPostUser() throws Exception{
		DmhUser dmh = new DmhUser();
		dmh.setWho("sdfgsdf");
		//DmhUser exampleEntity = mock(DmhUser.class);
		this.mockMvc
			.perform(get("/user/Adduser")
				.flashAttr("dmhUser", dmh))
			//.andDo(print())
		//.perform(with(anonymous()))
		//.andExpect(Model().attributeExists("dmhUser"));
		.andExpect(view().name("addUser"));
			
	}
	
	
	public void getAllUsersTest() throws Exception{
		
	}
	

}
