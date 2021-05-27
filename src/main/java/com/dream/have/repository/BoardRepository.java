package com.dream.have.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dream.have.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
