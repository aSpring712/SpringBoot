package com.dream.have.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dream.have.model.Board;
import com.dream.have.model.User;
import com.dream.have.repository.BoardRepository;
import com.dream.have.repository.UserRepository;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping("/users")
	List<User> all() {
		List<User> users = repository.findAll(); // 사용자 정보 조회
		Log.debug("getBoards().size() 호출전");
		Log.debug("getBoards().size() 호출후");
		return users; // 전체 조회
	}
	
	@PostMapping("/users")
	User newUser(@RequestBody User newUser) {
		return repository.save(newUser);
	}
	
	// Single item
	
	@GetMapping("/users/{id}")
	User one(@PathVariable Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@PutMapping("/users/{id}")
	User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
		return repository.findById(id)
				.map(user -> {
//					user.setTitle(newUser.getTitle());
//					user.setContent(newUser.getContent());
//					user.setBoards(newUser.getBoards());
					user.getBoards().clear();
					user.getBoards().addAll(newUser.getBoards());
					for(Board board : user.getBoards()) {
						board.setUser(user); // id로 조회한 user를 담아줌
					}
					return repository.save(user);
				})
				.orElseGet(() -> {
					newUser.setId(id);
					return repository.save(newUser);
				});
	}
	
	@DeleteMapping("/users/{id}")
	void deleteUser(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
