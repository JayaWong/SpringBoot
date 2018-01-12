package com.jaya.demo;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.jaya.model.Leave;
import com.jaya.model.User;
import com.jaya.service.ActivitiService;
import com.jaya.service.LeaveService;
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
	@Resource
	private LeaveService leaveService;
	@Resource
	private ActivitiService activitiService;
	/**
	 * <p>Description:[hello world]</p>
	 * Created on 2017年12月20日
	 * @param model 模型
	 * @return html
	 * @author:[王剑英]
	 */
	@GetMapping("/index")
	public String getHello(Model model,HttpServletRequest request) {
		model.addAttribute("name", "这是一个神奇的网站!");
		User user = (User)request.getSession().getAttribute("user");
		if (user!=null) {
			List<Leave> list = this.leaveService.getList(user);
			model.addAttribute("dataList", list);
			model.addAttribute("user", user);
		}
		return "index";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/doLogin")
	public String doLogin(Model model,User user,HttpServletRequest request) {
		User result = this.userService.login(user);
		if(result != null ) {
			request.getSession().setAttribute("user", result);
			return "redirect:index";
		}else {
			return "login";
		}
		
	}
	@PostMapping("/applyLeave")
	public String applyLeave(Model model,Leave leave,@SessionAttribute(name="user")User user) {
		leave.setLeaveDate(new Date());
		leave.setStatus(0);
		this.leaveService.save(user, leave);
		return "redirect:index";
	}
	@PostMapping("/confirmLeave")
	public void confirmLeave(Integer id,@SessionAttribute(name="user") User user) {
		Leave leave = new Leave();
		leave.setId(id);
		leave.setUser(user);
		this.activitiService.startProcess(leave);
	}
}
