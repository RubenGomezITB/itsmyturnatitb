package com.itb.itsmyturn.repository;

import com.itb.itsmyturn.model.SubjectStudents;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectstudentsRepository extends JpaRepository<SubjectStudents, Integer>{
    @Override
    <S extends SubjectStudents> List<S> findAll(Example<S> example);


}