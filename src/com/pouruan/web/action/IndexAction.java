package com.pouruan.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexAction {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String addRole(
			HttpServletRequest request,HttpServletResponse response, ModelMap model){
		
		return "index";
	}
}
