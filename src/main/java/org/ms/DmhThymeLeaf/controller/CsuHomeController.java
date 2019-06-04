package org.ms.DmhThymeLeaf.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.ms.DmhThymeLeaf.entity.CSULicensed;
import org.ms.DmhThymeLeaf.entity.CurrentUser;
import org.ms.DmhThymeLeaf.forms.CsuAdminForm;
import org.ms.DmhThymeLeaf.service.CsuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CsuHomeController {
	
	Logger logger = LoggerFactory.getLogger(CsuHomeController.class);
	
	@Autowired
	CsuService csuSrevice;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value= {"/csuHome"})
	public String csuHome(){
		return "csuHomePage";
	}
	
	
	/*@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value= {"/csu/adminLoad"}, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ModelAndView csuAdminLoad(){
		csuSrevice.getCSUProviders();
		
		return "csuAdminPage";
	}*/
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value= {"/csu/adminLoad"})
	public String csuAdminLoad(Model model, HttpSession session){
		
		logger.debug("in CsuAdminLoad Controller");
		
		List<String> providers = csuSrevice.getCSUProviders();
		Map<String, String> provMap =  providers.stream().map(prov -> prov.split("-")).collect(Collectors.toMap(e -> e[0], e -> e[1]));
		CsuAdminForm csuForm = new CsuAdminForm();
		/*ModelAndView mav = new ModelAndView("csuAdminPage");
	    mav.addObject("providers", provMap);*/
		//csuForm.setProvMap(provMap);
		session.setAttribute("csuAdminProviders", provMap);
		model.addAttribute("csuForm", csuForm);
		return "csuAdminPage";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value= {"/csu/adminLoadLocation"}, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<CSULicensed> csuAdminLoad (@RequestParam("provider") String provider, Authentication authentication){
		System.out.println("----------------------------");
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		logger.debug(user.getOrg());
		return csuSrevice.getCSULocations(provider);
		
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/csu/submitCsuAdmin", method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String processForm(@ModelAttribute CsuAdminForm csuAdminForm) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<CSULicensed> licBeds = objectMapper.readValue(csuAdminForm.getVoodoo(), new TypeReference<List<CSULicensed>>(){});
		System.out.println(licBeds);
		csuSrevice.insertCsuLicenseBeds(licBeds);
		return "csuAdminPage";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value= {"/csu/csuUsers"})
	public String csuUsersHome(){
		return "csuUsers";
	}
	
}
