package com.example.SA.Controller;

import com.example.SA.Service.UserService;
import com.example.SA.domain.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        model.put("message", "****");

        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        if (!userService.addUserSuccessful(user)){
            model.put("message", "User is already exists");
            return "registration";
        }
            return "redirect:/login";
    }

    @GetMapping("/activation/{code}")
    public String activationAccount(Model model, @PathVariable String codeOfActivation)
    {
        return "login";
    }
}
