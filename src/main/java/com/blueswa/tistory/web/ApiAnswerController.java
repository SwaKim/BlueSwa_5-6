package com.blueswa.tistory.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueswa.tistory.domain.Answer;
import com.blueswa.tistory.domain.AnswerRepository;
import com.blueswa.tistory.domain.Question;
import com.blueswa.tistory.domain.QuestionRepository;
import com.blueswa.tistory.domain.Result;
import com.blueswa.tistory.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")	///questions/{questionId}는 없어도 되지만
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인 해야합니다.");
		}
		
		Answer answer = answerRepository.findOne(id);
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("작성자만 삭제할 수 있습니다.");
		}
		
		answerRepository.delete(id);
		return Result.ok();
	}
}
