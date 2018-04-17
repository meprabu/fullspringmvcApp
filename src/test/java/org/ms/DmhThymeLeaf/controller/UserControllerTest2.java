package org.ms.DmhThymeLeaf.controller;



import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ms.DmhThymeLeaf.UserDetailsServiceMock;
import org.ms.DmhThymeLeaf.Dao.BcUserDAO;
import org.ms.DmhThymeLeaf.Dao.UserServiceDao;
import org.ms.DmhThymeLeaf.entity.DmhUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest2 {
	
	@Autowired
    private MockMvc mockMvc;
	
	
	
	@MockBean
	private UserServiceDao userDao;
	
	@MockBean
	private BcUserDAO bcdao;;
	
	
	@Test
	@WithMockUser
	public void testGotoUser() throws Exception{
		this.mockMvc.perform(get("/user/Adduser"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("dmhUser"))
		.andExpect(view().name("addUser"));
			
	}
	
	@Test
	@WithMockUser(username = "dmh.prabu.jayapandia", password = "pwd", roles = "USER")
	public void testviewUser() throws Exception{
		//user();
		this.mockMvc.perform(get("/user")).andExpect(view().name("user"));
			
	}
	
	//@Test
	@WithMockUser(username = "dmh.prabu.jayapandia", password = "pwd", roles = "USER")
	public void testGotoPostUser() throws Exception{
		DmhUser dmh = new DmhUser();
		dmh.setWho("sdfgsdf");
		//DmhUser exampleEntity = mock(DmhUser.class);
		this.mockMvc
			.perform(post("/user/Adduser")
				.param("who", "dmh.prabu.jayapandia"))
			.andExpect(model().attributeHasFieldErrors("dmhUser", "first_name"));
			// .andDo(print())
		//.perform(with(anonymous()))
		//.andExpect(model().attributeExists("dmhUser"));
		//.andExpect(view().name("addUser"));
			
	}
	
	
	@WithMockUser(username = "dmh.prabu.jayapandia", password = "pwd", roles = "USER")
	public void getAllUsersTest() throws Exception{
		
	}
	
}
