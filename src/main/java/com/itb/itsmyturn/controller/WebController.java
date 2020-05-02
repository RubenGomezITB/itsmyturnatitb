package com.itb.itsmyturn.controller;

import com.itb.itsmyturn.model.Users;
import com.itb.itsmyturn.model.Webuser;
import com.itb.itsmyturn.repository.SubjectsRepository;
import com.itb.itsmyturn.repository.TurnsRepository;
import com.itb.itsmyturn.repository.UsersRepository;
import com.itb.itsmyturn.service.TurnService;
import com.itb.itsmyturn.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    @Autowired
    private WebUserService service;
    @Autowired
    private SubjectsRepository subjectsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/register")
    public String register(Model m){
        m.addAttribute("userForm", new Webuser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Webuser user) throws Exception{
        service.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/admin/subjects/list")
    public String getAllSubjects(Model m){
        m.addAttribute("SubjectList", subjectsRepository.findAll());
        return "subjectlist";
    }

    @GetMapping("/admin/users/list")
    public String getAllUsers(Model m){
        m.addAttribute("UserList", usersRepository.findAll());
        return "users";
    }

    @GetMapping("/admin/users/delete")
    public String eliminarEmpleat(@RequestParam int id){
        service.delete(id);
        return "redirect:/admin/users/list";
    }

    @PostMapping("/admin/users/edit/submit")
    public String substituirSubmit(@ModelAttribute("userForm") Users user){
        service.modify(user);
        return "redirect:/admin/users/list";
    }

    @GetMapping("/admin/users/edit/{email}")
    public String substituirEmpleat(@PathVariable String email, Model m){
        Users user=usersRepository.findByEmail(email);
        m.addAttribute("userForm", user);
        return "editUser";
    }
}
