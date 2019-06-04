package org.ms.DmhThymeLeaf.controller;

import java.util.Optional;

import org.ms.DmhThymeLeaf.entity.CurrentUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

	@GetMapping(value = { "/", "/login" })
	public String rootController() {
		return "login";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = { "/home" })
	public String homePage(Authentication authentication) {
		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		if (Optional.ofNullable(user.getOrg()).isPresent() && user.getOrg().equals("999")) {
			return "home";
		}
		if (Optional.ofNullable(user.getCsu()).isPresent() && user.getCsu().equals("Y")) {
			return "forward:/csuHome";
		} else if (Optional.ofNullable(user.getCl()).isPresent() && user.getCl().equals("Y")) {
			return "clHome";
		} else if (Optional.ofNullable(user.getIdd()).isPresent() && user.getIdd().equals("Y")) {
			return "iddHome";
		}
		return "home";
	}

}
