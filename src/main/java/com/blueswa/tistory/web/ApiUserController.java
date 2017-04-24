package com.blueswa.tistory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueswa.tistory.domain.User;
import com.blueswa.tistory.domain.UserRepository;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	private UserRepository UserRepository;
	
	@GetMapping("/{id}")
	public User show(@PathVariable Long id) {
		return UserRepository.findOne(id);
	}

}
