package org.ms.DmhThymeLeaf.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	
	@ExceptionHandler(Exception.class)
    public ModelAndView  exception(Exception e) {
		
		return new ModelAndView("error","errorMsg","An error occured. Please contact Admin!");
    }
	
	@ExceptionHandler(DuplicateKeyException.class)
    public String  newuplicateKeyException(Model model) {
		
		model.addAttribute("errorMsg", "The user already exsists!");
		return "error";
    }
	
	
	
	

}
