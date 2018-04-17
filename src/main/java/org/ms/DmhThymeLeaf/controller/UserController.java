package org.ms.DmhThymeLeaf.controller;

import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.ms.DmhThymeLeaf.Dao.BcUserDAO;
import org.ms.DmhThymeLeaf.Dao.UserServiceDao;
import org.ms.DmhThymeLeaf.entity.BcUser;
import org.ms.DmhThymeLeaf.entity.DmhUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	
	@Autowired
	BcUserDAO bcdao;
	
	@Autowired
	UserServiceDao userDao;
	
	public void InitBinder(WebDataBinder binder){
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		//binder.setFieldDefaultPrefix(fieldDefaultPrefix);
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Secured("ROLE_USER")
	@GetMapping(value= {"/user"})
	public String getUser(){
		//System.out.println(principal.getName());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		auth.getPrincipal();
		List<BcUser>  users = bcdao.getAllUsers();
		return "user";
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value= {"/user/Adduser"})
	public String addUser(@ModelAttribute @Valid DmhUser dmhUser, BindingResult bindingResult, ModelMap model) throws Exception{
		//System.out.println(principal.getName());
		System.out.println("The System has Binding errors");
		
		if(bindingResult.hasErrors()){
			System.out.println("The System has Binding errors");
			return "addUser";

		}else{
			userDao.updateUser(dmhUser);
			model.addAttribute("userName", dmhUser.getWho());
			model.addAttribute("First_Name",dmhUser.getFirst_name());
			model.addAttribute("last_Name", dmhUser.getLast_name());
			return "userAddSuccess";
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value= {"/user/Adduser"})
	public String gotoAddUser(Model model){
		model.addAttribute("dmhUser", new DmhUser());
		return "addUser";
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value= {"/user/getAllUsers"})
	public String getAllUsers(Model model){
		Optional<List<DmhUser>> dmhuser = userDao.getAllusers();
		List<DmhUser> list = null;
		if(dmhuser.map(List::size).filter(s-> s > 0).isPresent()){
			
			list = dmhuser.get().stream().filter(s -> !StringUtils.isEmpty(s.getFirst_name())).collect(Collectors.toList());
			
			model.addAttribute("allUsersList", list);
		}
		return "allUsers";
		
	}
	
	@ExceptionHandler(Exception.class)
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value= {"/user/searchUsers"})
	public @ResponseBody List<DmhUser> searchUsers(@RequestParam String first_name){
		Optional<List<DmhUser>> dmhuser = userDao.getAllusers();
		List<DmhUser> searchList = dmhuser.get().stream().filter(s -> !StringUtils.isEmpty(s.getFirst_name())).filter(s->s.getFirst_name().contains(first_name)).collect(Collectors.toList());
		return searchList;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value= {"/user/getSearchUsers"})
	public String getSearchUsers(){
		return "searchUser";
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public void handleException(final Exception e, final HttpServletRequest request,
	        Writer writer) throws Exception
	{
	    writer.write(String.format(
	            "{\"error\":{\"java.class\":\"%s\", \"message\":\"%s\"}}",
	            e.getClass(), e.getMessage()));
	}
	

}
