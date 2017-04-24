package com.blueswa.tistory.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomController {
	@GetMapping("/helloworld")
	public String welcome(String name, int age,Model model) {	//메소드는 상관없음
		System.out.println("name : "+ name + "age : " + age);
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "welcome";	//리턴값이 중요
	}

}
