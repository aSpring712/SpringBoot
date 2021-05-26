package com.dream.have.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // view를 return 하겠다
public class HomeController {
	
	@GetMapping
	public String index() {
		return "index";
	}
}
