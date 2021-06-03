package com.dream.have.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dream.have.model.User;
import com.dream.have.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "account/login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "account/register";
	}
	
	// 로그인 완료 시 홈으로 이동
	@PostMapping("/register")
	public String register(User user) {
		userService.save(user);
		return "redirect:/";
	}
}
