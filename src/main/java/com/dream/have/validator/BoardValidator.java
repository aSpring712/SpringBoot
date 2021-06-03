package com.dream.have.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dream.have.model.Board;

@Component
public class BoardValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Board.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) { // obj의 field 값이 빈 값인지 아닌지 체크
		Board b = (Board) obj;
		if(StringUtils.isEmpty(b.getContent())) {
			errors.rejectValue("content", "key", "내용을 입력하세요");
		}
	}
	
}
