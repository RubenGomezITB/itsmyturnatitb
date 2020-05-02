package com.itb.itsmyturn.controller;

import com.itb.itsmyturn.model.*;
import com.itb.itsmyturn.repository.SubjectsRepository;
import com.itb.itsmyturn.repository.SubjectstudentsRepository;
import com.itb.itsmyturn.repository.TurnsRepository;
import com.itb.itsmyturn.repository.UsersRepository;
import com.itb.itsmyturn.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class APIController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TurnService turnService;

    @GetMapping("login/{user}/{passwd}")
    public AuthToken getAuthUser(@PathVariable(value = "user") String email, @PathVariable(value = "passwd") String passwd) {
        return turnService.getAuthUser(email, passwd);
    }

    @GetMapping("register")
    public RegisterAuth registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String passwd, @RequestParam boolean isTeacher) {
        turnService.registerAuth(name, email, passwd, isTeacher);
        return new RegisterAuth("true");
    }

    @GetMapping("user/isteacher")
    public Boolean getIsTeacher(@RequestParam String email) {
        return turnService.isTeacher(email);
    }

    @GetMapping("class/student")
    public SubjectsList getSubjectByStudent(@RequestParam String email) {
        return turnService.getSubjectList(email);
    }

    @GetMapping("class/teacher")
    public SubjectsList getSubjectByTeacher(@RequestParam String email) {
        return turnService.getSubjectByTeacher(email);
    }

    @GetMapping("class/turns")
    public TurnList getTurnListByClass(@RequestParam int id) {
        return turnService.getTurnListByClass(id);
    }

    @GetMapping("class/turndescription")
    public Turns getTurnById(@RequestParam int id) {
        return turnService.getTurnDescription(id);
    }

    @GetMapping("user/user")
    public String getNameByEmail(@RequestParam String email) {
        return usersRepository.findByEmail(email).getName();
    }

    @GetMapping("class/newturn")
    public String insertTurn(@RequestParam String email, int idSubject, String time, String description) {
        turnService.insertTurns(email, idSubject, time, description);
        return "User insert done";
    }

    @GetMapping("class/newsubject")
    public int insertSubject(@RequestParam String subjectName, String idGroup, String email) {
        return turnService.insertSubject(subjectName, idGroup, email);
    }

    @GetMapping("class/joinclass")
    public String joinClass(@RequestParam int idSubject, String email) {
        turnService.joinClass(idSubject, email);
        return "Join class done";
    }

    @GetMapping("class/deleteturn")
    public String deleteTurn(@RequestParam int idTurn) {
        turnService.deleteTurn(idTurn);
        return "Deleted turn";
    }

}
