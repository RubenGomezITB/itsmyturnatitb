package com.itb.itsmyturn.repository;

import com.itb.itsmyturn.model.Users;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer>{

    @Query(value = "from Users where email = ?1")
    Users findByEmail(String emailVariable);

    @Override
    <S extends Users> S save(S s);

    @Override
    Optional<Users> findById(Integer integer);

    @Override
    List<Users> findAll();
}