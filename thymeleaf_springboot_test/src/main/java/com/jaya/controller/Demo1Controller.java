package com.jaya.controller;

import com.jaya.function.IScopFunction;
import com.jaya.function.ScopFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/demo")
public class Demo1Controller {

	@Autowired
	private IScopFunction scopFunction;

	@GetMapping("/test1")
	public String test1(String s) {
		String r = null;
		for (int i = 0; i < 5; i++) {
			r = scopFunction.apply(s);
		}
		return r;
	}

}
