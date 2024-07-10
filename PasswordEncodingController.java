package com.javiera.alke.controller;

import com.javiera.alke.model.User;
import com.javiera.alke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordEncodingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("customPasswordEncoder")
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/encode-passwords")
    public String encodePasswords() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            // Check if the password is already encoded
            if (!user.getPassword().startsWith("$2a$")) {
                String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
            }
        }
        return "redirect:/login";
    }
}
