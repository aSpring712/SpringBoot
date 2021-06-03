package com.dream.have.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dream.have.model.Role;
import com.dream.have.model.User;
import com.dream.have.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User save(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword()); // 비밀번호 암호화
		user.setPassword(encodedPassword); // 암호화 된 비밀번호 저장
		user.setEnabled(true); // 가입 시 기본적으로 활성화
		Role role = new Role();
		role.setId(1l);
		user.getRoles().add(role);
		return userRepository.save(user);
	}
}
