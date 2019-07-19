package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showForm(Model model) {
       model.addAttribute("user",new User());
       return "index";
    }

    @PostMapping("/")
    public String checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, Pageable pageable) {
        new User().validate(user,bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "index";
        } else {

            userService.save(user);
            Page<User> users = userService.findAll(pageable);
            model.addAttribute("users",users);
            return "result";
        }
    }

}
