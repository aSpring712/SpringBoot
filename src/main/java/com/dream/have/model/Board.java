package com.dream.have.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data // getter, setter 자동 생성
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=2, max=30, message = "제목은 2자이상 30자 이하입니다.")
	private String title;
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore // 서로 참조하다보니 재귀적으로 호출 -> 끊어주기
	private User user;
}
