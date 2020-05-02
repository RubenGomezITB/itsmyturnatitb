package com.itb.itsmyturn.repository;

import com.itb.itsmyturn.model.Webuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WebuserRepository extends JpaRepository<Webuser, String>{
    Webuser findByEmail(String email);
}