package com.xendv.ReportLoader.controllers;

import com.xendv.ReportLoader.model.SystemUser;
import com.xendv.ReportLoader.service.security.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new SystemUser());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid SystemUser userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
/*        if (!userForm.getPassword().equals(userForm.getPassword())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }*/
        if (userService.create(userForm) == null) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}
