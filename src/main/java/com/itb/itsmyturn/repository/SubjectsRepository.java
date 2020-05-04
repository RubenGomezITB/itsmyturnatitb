package com.itb.itsmyturn.repository;

import com.itb.itsmyturn.model.Subjects;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.security.auth.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectsRepository extends JpaRepository<Subjects, Integer> {
    @Override
    Optional<Subjects> findById(Integer integer);

    @Query(value = "from Subjects where idTeacher = ?1")
    List<Subjects> findById(int id);

    @Query(value = "from Subjects where id = ?1")
    Subjects findOneByPrimaryKey(int id);

    @Override
    <S extends Subjects> List<S> findAll(Example<S> example);

    @Override
    <S extends Subjects> S save(S s);

    @Override
    void delete(Subjects subjects);

    @Override
    void deleteById(Integer integer);
}