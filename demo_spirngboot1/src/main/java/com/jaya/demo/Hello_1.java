package com.jaya.demo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jaya.model.User;
import com.jaya.service.UserService;


@Controller
@RequestMapping("/thymeleaf")
public class Hello_1 {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(Hello_1.class);
	@Resource
	private UserService userService;
	/**
	 * <p>Description:[hello world]</p>
	 * Created on 2017年12月20日
	 * @param model 模型
	 * @return html
	 * @author:[王剑英]
	 */
	@GetMapping("/index")
	public String getHello(Model model,HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		if(user != null) {
			return "demo1";
		}else {
			return "login";
		}
	}
	@PostMapping("/registry")
	public String  registry(User user,Model model) {
		User register = this.userService.register(user);
		return "login";
	}
	@GetMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	@GetMapping("/toRegistry")
	public String toRegistry() {
		return "registry";
	}
	@PostMapping("/login")
	public String login(User user,HttpServletRequest request,Model model) {
		User result = this.userService.login(user);
		if(result != null ) {
			request.getSession().setAttribute("user", result);
			model.addAttribute("user", result);
			return "demo1";
		}else {
			return "login";
		}
	}
}
