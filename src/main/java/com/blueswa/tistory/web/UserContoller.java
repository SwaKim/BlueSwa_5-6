package com.blueswa.tistory.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blueswa.tistory.domain.User;
import com.blueswa.tistory.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserContoller {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {			//컨트롤 시프트 O httpsession 임포트
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";
		}
		
//		if (!password.equals(user.getPassword())) { //get패스워드 필요없어짐 User.java boolean mathc
		if (!user.matchPassword(password)) {
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("Login Success!");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user) { //자바 그이하내용
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
//		Object tempUser = session.getAttribute("sessionedUser"); //세선에서 값을 가져오면 object값으로 관리 //4-4 java함수로 구현
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
//		if (!id.equals(sessionedUser.getId())) { //4-4get Id를 하는것보다
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can't update the another user");
		}
		
		User user = userRepository.findOne(id); //sessionedUser.getId()로 사용하면 바로 위의 두줄 조건문이 필요없다
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}") //3-5 updateForm에서 _method 지정, ctrl + shift + O
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
//		Object tempUser = session.getAttribute("sessionedUser"); //세선에서 값을 가져오면 object값으로 관리 //4-4 java함수로 구현
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
//		if (!id.equals(sessionedUser.getId())) {
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You can't update the another user");
		}
				
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
