package com.itb.itsmyturn.service;

import com.itb.itsmyturn.model.*;
import com.itb.itsmyturn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.spi.RegisterableService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnService {
    String authToken = "A1B2C3D4E5";
    String error = "Error";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SubjectstudentsRepository subjectstudentsRepository;
    @Autowired
    private SubjectsRepository subjectsRepository;
    @Autowired
    private TurnsRepository turnsRepository;
    @Autowired
    private WebuserRepository webuserRepository;

    public AuthToken getAuthUser(String email, String passwd) {
        Users user = usersRepository.findByEmail(email);
        AuthToken auth = new AuthToken();
        if (user != null) {
            if (passwordEncoder.matches(passwd, user.getPassword())) {
                auth.setAuth(authToken);
                return auth;
            } else return auth;
        }
        return auth;
    }

    public void registerAuth(String name, String email, String passwd, boolean isTeacher) {
        List<Users> usersList = usersRepository.findAll();
        int id = (usersList.get(usersList.size() - 1).getId()) + 1;
        Users user = new Users(id, name, email, passwordEncoder.encode(passwd), isTeacher);
        boolean exists = false;
        for (int i = 0; i <= usersList.size(); i++) {
            if (usersList.get(i).getEmail().equals(email)) {
                exists = true;
            }
        }
        if (!exists) {
            usersRepository.save(user);
        }
    }

    public boolean isTeacher(String email) {
        Users users = usersRepository.findByEmail(email);
        return users.isTeacher();
    }

    public SubjectsList getSubjectList(String email) {
        List<SubjectStudents> subjectStudentsList = subjectstudentsRepository.findAll();
        Users users = usersRepository.findByEmail(email);
        List<Subjects> subjectsList = new ArrayList<>();
        for (int i = 0; i < subjectStudentsList.size(); i++) {
            if (subjectStudentsList.get(i).getIdStudent() == users.getId()) {
                subjectsList.add(subjectsRepository.findById(subjectStudentsList.get(i).getIdSubject()).get());
            }
        }
        return new SubjectsList(subjectsList);
    }

    public SubjectsList getSubjectByTeacher(String email) {
        int id = usersRepository.findByEmail(email).getId();
        List<Subjects> subjectList = subjectsRepository.findById(id);
        return new SubjectsList(subjectList);
    }

    public TurnList getTurnListByClass(int id) {
        List<Turns> turnsList = turnsRepository.findByClassId(id);
        List<TurnResponse> turnResponseList = new ArrayList<>();
        for (int i = 0; i < turnsList.size(); i++) {
            int idTurn = turnsList.get(i).getId();
            String name = usersRepository.findById(turnsList.get(i).getIdStudent()).get().getName();
            String time = turnsList.get(i).getTime();
            String description = turnsList.get(i).getDescription();
            turnResponseList.add(new TurnResponse(idTurn, name, time, description));
        }
        return new TurnList(turnResponseList);
    }

    public Turns getTurnDescription(int id) {
        Optional<Turns> optionalTurns = turnsRepository.findById(id);
        return optionalTurns.orElseGet(() -> new Turns(0, 0, 0, "0", "This turn has been deleted"));
    }

    public void insertTurns(String email, int idSubject, String time, String description) {
        List<Turns> turnsList = turnsRepository.findAll();
        int id;
        if (turnsList.size() > 0) {
            id = (turnsList.get(turnsList.size() - 1).getId()) + 1;
        } else {
            id = 1;
        }
        int idStudent = usersRepository.findByEmail(email).getId();
        Turns turn = new Turns(id, idStudent, idSubject, time, description);
        turnsRepository.save(turn);
    }

    public int insertSubject(String subjectName, String idGroup, String email) {
        List<Subjects> subjectsList = subjectsRepository.findAll();

        int id;
        if (subjectsList.size() > 0) {
            id = (subjectsList.get(subjectsList.size() - 1).getId()) + 1;
        } else {
            id = 1;
        }
        int idTeacher = usersRepository.findByEmail(email).getId();
        Subjects subject = new Subjects(id, idTeacher, subjectName, idGroup);
        subjectsRepository.save(subject);
        return subject.getId();
    }

    public void joinClass(int idSubject, String email) {
        List<SubjectStudents> subjectStudentsList = subjectstudentsRepository.findAll();
        int id = (subjectStudentsList.get(subjectStudentsList.size() - 1).getId()) + 1;
        int idStudent = usersRepository.findByEmail(email).getId();
        SubjectStudents subjectStudents = new SubjectStudents(id, idSubject, idStudent);
        subjectstudentsRepository.save(subjectStudents);
    }

    public void deleteTurn(int idTurn) {
        Turns turn = turnsRepository.findById(idTurn).get();
        turnsRepository.delete(turn);
    }
}
