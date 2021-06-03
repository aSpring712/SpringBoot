package com.dream.have.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String password;
	private Boolean enabled;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>(); 
	
	// orphanRemoval 기본값 : false -> true로 주면 
//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // 양방향 mapping 서로 조회 가능, 사용자 변경 시 게시글도 변경(Cascade), 부모가 없는 데이터는 지운다
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // boards를 사용 시 데이터가 조회됨
//	@JsonIgnore
	private List<Board> boards = new ArrayList<>();
}
