package com.dream.have.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dream.have.model.Board;
import com.dream.have.repository.BoardRepository;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired // DI 일어남
	private BoardRepository boardRepository;
	
	// 게시글 목록으로 이동
	@GetMapping("/list")
	public String list(Model model) {
		List<Board> boards = boardRepository.findAll();
		model.addAttribute("boards", boards);
		return "board/list";
	}
	
	// 글쓰기 form으로 이동 / 수정 폼으로 이동
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long id) {
		if(id == null) {
			model.addAttribute("board", new Board());
		} else {
			Board board = boardRepository.findById(id).orElse(null); // 조회된 값이 없으면 null로 처리
			model.addAttribute("board", board);
		}
		
		return "board/form";
	}
	
	// 글쓰기 버튼 클릭 시 DB에 값 저장 / 수정
	@PostMapping("/form")
	public String insert(@Valid Board board, BindingResult bindingResult) { // bindingResult가 위에서 선언한 조건에 부합하는지 체크
		if(bindingResult.hasErrors()) {
			return "board/form";
		}
		boardRepository.save(board);
		return "redirect:/board/list"; // 글 목록으로 이동 시 값을 한번 더 조회하고 이동할 수 있도록
	}
}

