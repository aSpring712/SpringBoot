package com.dream.have.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.have.model.Board;
import com.dream.have.model.User;
import com.dream.have.repository.BoardRepository;
import com.dream.have.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Board save(String username, Board board) {
		User user = userRepository.findByUsername(username); // username 으로 id 조회
		board.setUser(user);
		return boardRepository.save(board);
	}
}
