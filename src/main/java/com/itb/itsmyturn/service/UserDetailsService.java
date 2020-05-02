package com.itb.itsmyturn.service;

import com.itb.itsmyturn.model.Webuser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private WebUserService webUserService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Webuser u= webUserService.findById(email);
        User.UserBuilder builder=null;
        if(u!=null){
            builder=User.withUsername(email);
            builder.disabled(false);
            builder.password(u.getPassword());
            builder.authorities(new SimpleGrantedAuthority(u.getRol()));
            System.out.println(u.getRol());
        }
        else throw new Exception("User not found");
        return builder.build();
    }



}
