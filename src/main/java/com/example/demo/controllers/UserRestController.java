package com.example.demo.controllers;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import es.ucm.fdi.bd.utils.JdbcUtils;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private UserRepository userRepository;

    @Autowired
    public UserRestController(Collection<UserRepository> userRepository) {
        this.userRepository = userRepository.iterator().next();
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> us = null;
    	try {
			us = userRepository.getAll();
		} catch (SQLException e) {
			JdbcUtils.printSQLException(e);
		}
    	
    	return us;
    }

    @RequestMapping(value="register", method=RequestMethod.GET)
    public String createUser() {
        return "formularioRegistro";
    }
}