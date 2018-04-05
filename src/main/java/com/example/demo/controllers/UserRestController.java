package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private UserRepository userRepository;

    @Autowired
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @RequestMapping(value="register", method=RequestMethod.GET)
    public String createUser() {
        return "formularioRegistro";
    }
}