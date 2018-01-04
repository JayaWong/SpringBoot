package com.jaya.demo;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/thymeleaf")
public class Hello_1 {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(Hello_1.class);
	
	/**
	 * <p>Description:[hello world]</p>
	 * Created on 2017年12月20日
	 * @param model 模型
	 * @return html
	 * @author:[王剑英]
	 */
	@GetMapping("/getHello")
	public String getHello(Model model,HttpServletRequest request) {
		model.addAttribute("name", "这是一个神奇的网站!");
		if (request.getSession().getAttribute("user")!=null) {
			model.addAttribute("user", request.getSession().getAttribute("user"));
		}
		return "index";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/doLogin")
	public String doLogin(Model model,String userName,String passWord,HttpServletRequest request) {
		request.getSession().setAttribute("user", userName);
		model.addAttribute("user", userName);
		return "redirect:getHello";
	}
}
