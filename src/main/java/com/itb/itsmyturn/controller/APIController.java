package com.itb.itsmyturn.controller;

import com.itb.itsmyturn.model.*;
import com.itb.itsmyturn.repository.SubjectsRepository;
import com.itb.itsmyturn.repository.SubjectstudentsRepository;
import com.itb.itsmyturn.repository.TurnsRepository;
import com.itb.itsmyturn.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class APIController {
    String authToken = "A1B2C3D4E5";
    String error = "Error";
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SubjectstudentsRepository subjectstudentsRepository;
    @Autowired
    private SubjectsRepository subjectsRepository;
    @Autowired
    private TurnsRepository turnsRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("login/{user}/{passwd}")
    public AuthToken getAuthUser(@PathVariable(value = "user") String email, @PathVariable(value = "passwd") String passwd) {
        Users user = usersRepository.findByEmail(email);

        //if (passwordEncoder.matches(passwd, user.getPassword()));
        AuthToken auth= new AuthToken();
        if(user!=null){
            if (passwordEncoder.matches(passwd, user.getPassword())) {
                auth.setAuth(authToken);
                return auth;
            } else return auth;
        }
        return auth;
    }

    @GetMapping("register")
    public RegisterAuth registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String passwd, @RequestParam boolean isTeacher){
        List<Users> usersList = usersRepository.findAll();
        int id = (usersList.get(usersList.size()-1).getId())+1;
        Users user = new Users(id,name, email, passwordEncoder.encode(passwd), isTeacher);
        usersRepository.save(user);
        return new RegisterAuth("true");
    }

    @GetMapping("user/isteacher")
    public Boolean getIsTeacher(@RequestParam String email){
        Users users = usersRepository.findByEmail(email);
        return users.isTeacher();
    }

    @GetMapping("class/student")
    public SubjectsList getSubjectByStudent(@RequestParam String email){
        List<SubjectStudents> subjectStudentsList = subjectstudentsRepository.findAll();
        Users users = usersRepository.findByEmail(email);
        List<Subjects> subjectsList = new ArrayList<>();
        for(int i=0; i<subjectStudentsList.size();i++){
            if(subjectStudentsList.get(i).getIdStudent() == users.getId()){
                subjectsList.add(subjectsRepository.findById(subjectStudentsList.get(i).getIdSubject()).get());
            }
        }
        return new SubjectsList(subjectsList);
    }

    @GetMapping("class/teacher")
    public SubjectsList getSubjectByTeacher(@RequestParam String email){
        int id = usersRepository.findByEmail(email).getId();
        List<Subjects> subjectList = subjectsRepository.findById(id);
        return new SubjectsList(subjectList);
    }

    @GetMapping("class/turns")
    public TurnList getTurnsByClass(@RequestParam int id){

        List<Turns> turnsList = turnsRepository.findByClassId(id);
        List<TurnResponse> turnResponseList = new ArrayList<>();
        for(int i=0; i<turnsList.size();i++){
            int idTurn = turnsList.get(i).getId();
            String name = usersRepository.findById(turnsList.get(i).getIdStudent()).get().getName();
            String time = turnsList.get(i).getTime();
            String description = turnsList.get(i).getDescription();
            turnResponseList.add(new TurnResponse(idTurn,name,time,description));
        }
        return new TurnList(turnResponseList);
    }

    @GetMapping("class/turndescription")
    public Turns getTurnById(@RequestParam int id){
        Optional<Turns> optionalTurns = turnsRepository.findById(id);
        return optionalTurns.orElseGet(() -> new Turns(0, 0, 0, "0", "This turn has been deleted"));
    }

    @GetMapping("user/user")
    public String getNameByEmail(@RequestParam String email) {
        return usersRepository.findByEmail(email).getName();
    }

    @GetMapping("class/newturn")
    public String insertTurn(@RequestParam String email, int idSubject, String time, String description){
        List<Turns> turnsList = turnsRepository.findAll();
        int id;
        if(turnsList.size()>0){
            id = (turnsList.get(turnsList.size()-1).getId())+1;
        } else {
            id = 1;
        }
        int idStudent = usersRepository.findByEmail(email).getId();
        Turns turn = new Turns(id,idStudent,idSubject,time,description);
        turnsRepository.save(turn);
        return "User insert done";
    }

    @GetMapping("class/newsubject")
    public int insertSubject(@RequestParam String subjectName, String idGroup, String email){
        List<Subjects> subjectsList = subjectsRepository.findAll();

        int id;
        if(subjectsList.size()>0) {
            id = (subjectsList.get(subjectsList.size() - 1).getId()) + 1;
        }
        else {
            id = 1;
        }
        int idTeacher = usersRepository.findByEmail(email).getId();
        Subjects subject = new Subjects(id,idTeacher,subjectName,idGroup);
        subjectsRepository.save(subject);
        return id;
    }

    @GetMapping("class/joinclass")
    public String joinClass(@RequestParam int idSubject, String email){
        List<SubjectStudents> subjectStudentsList = subjectstudentsRepository.findAll();
        int id = (subjectStudentsList.get(subjectStudentsList.size()-1).getId())+1;
        int idStudent = usersRepository.findByEmail(email).getId();
        SubjectStudents subjectStudents = new SubjectStudents(id, idSubject, idStudent);
        subjectstudentsRepository.save(subjectStudents);
        return "Join class done";
    }

    @GetMapping("class/deleteturn")
    public String deleteTurn(@RequestParam int idTurn){
        Turns turn = turnsRepository.findById(idTurn).get();
        turnsRepository.delete(turn);
        return "Deleted turn";
    }

}
