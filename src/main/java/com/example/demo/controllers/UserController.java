package com.example.demo.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.impl.SecureUserRepository;

import es.ucm.fdi.bd.utils.JdbcUtils;

@Controller
public class UserController {
	private UserRepository userRepository;

	@Autowired
	public UserController(List<UserRepository> userRepository) {
		// Which use?
		this.userRepository = userRepository.get(0) instanceof SecureUserRepository ? userRepository.get(0)
				: userRepository.get(1); // I know... xD
	}
	
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {

		model.addAttribute("name", name);
		
		try {
			model.addAttribute("users", userRepository.getAll());
		} catch (SQLException e) {
			JdbcUtils.printSQLException(e);
		}

		return "greeting";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id", required = true) long id, Model model) {
		try {	
			userRepository.delete(id);
			userRepository.commit();
		} catch (SQLException e) {
			JdbcUtils.printSQLException(e);
		}
		return "redirect:/greeting";
	}

	@PostMapping("/register")
	public String register(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password, Model model) {
		try {
			userRepository.create(new User(null, username, password));
			userRepository.commit();
		} catch (SQLException e) {
			JdbcUtils.printSQLException(e);
		}
		return "redirect:/greeting";
	}
	
	@GetMapping("/registerg")
	public String registerGet(@RequestParam("username") String username,
			@RequestParam("password") String password, Model model) {
		try {
			userRepository.create(new User(null, username, password));
			userRepository.commit();
		} catch (SQLException e) {
			JdbcUtils.printSQLException(e);
		}
		return "greeting";
	}
}
