package com.itb.itsmyturn.service;

import com.itb.itsmyturn.model.Users;
import com.itb.itsmyturn.model.Webuser;
import com.itb.itsmyturn.repository.UsersRepository;
import com.itb.itsmyturn.repository.WebuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WebUserService {
    @Autowired
    private WebuserRepository webuserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    public void registerUser(Webuser user) throws Exception {
        Webuser u = webuserRepository.findByEmail(user.getEmail());
        if(u==null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRol("ADMIN");
            webuserRepository.save(user);
        }
        else throw new Exception("User already exists.");
    }

    public Webuser findById(String email) {
        return webuserRepository.findByEmail(email);
    }

    public void delete(int id){
        usersRepository.deleteById(id);
    }

    public void modify(Users user){
        usersRepository.save(user);
    }
}
