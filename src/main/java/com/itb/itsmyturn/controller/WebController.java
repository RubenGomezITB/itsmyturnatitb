package com.itb.itsmyturn.controller;

import com.itb.itsmyturn.model.Subjects;
import com.itb.itsmyturn.model.Users;
import com.itb.itsmyturn.model.Webuser;
import com.itb.itsmyturn.repository.SubjectsRepository;
import com.itb.itsmyturn.repository.UsersRepository;
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

    @GetMapping("/")
    public String root(){
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
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

    @GetMapping("/subjects/list")
    public String getAllSubjects(Model m){
        m.addAttribute("SubjectList", subjectsRepository.findAll());
        return "subjectlist";
    }

    @GetMapping("/users/list")
    public String getAllUsers(Model m){
        m.addAttribute("UserList", usersRepository.findAll());
        return "users";
    }

    @GetMapping("/admin/users/delete")
    public String eliminarEmpleat(@RequestParam int id){
        service.delete(id);
        return "redirect:/users/list";
    }

    @PostMapping("/admin/users/edit/submit")
    public String substituirSubmit(@ModelAttribute("userForm") Users user){
        service.modify(user);
        return "redirect:/users/list";
    }

    @GetMapping("/admin/users/edit/{email}")
    public String substituirEmpleat(@PathVariable String email, Model m){
        Users user=usersRepository.findByEmail(email);
        m.addAttribute("userForm", user);
        return "editUser";
    }

    @GetMapping("/admin/subjects/list/delete")
    public String eliminarSubject(@RequestParam int id){
        service.deleteSubject(id);
        return "redirect:/admin/subjects/list";
    }

    @PostMapping("/admin/subjects/list/edit/submit")
    public String modificarSubjectSubmit(@ModelAttribute("subjectForm") Subjects subjects){
        service.modifySubject(subjects);
        return "redirect:/admin/subjects/list";
    }

    @GetMapping("/admin/subjects/list/edit/{id}")
    public String modificarSubject(@PathVariable int id, Model m){
        Subjects subject = subjectsRepository.findOneByPrimaryKey(id);
        m.addAttribute("subjectForm", subject);
        return "editSubject";
    }
}
