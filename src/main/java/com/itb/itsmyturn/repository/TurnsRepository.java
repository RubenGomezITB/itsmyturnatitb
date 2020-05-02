package com.itb.itsmyturn.repository;

import com.itb.itsmyturn.model.TurnResponse;
import com.itb.itsmyturn.model.Turns;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TurnsRepository extends JpaRepository<Turns, Integer> {
    @Query(value = "from Turns where idSubject = ?1")
    List<Turns> findByClassId(int id);

    @Override
    Optional<Turns> findById(Integer integer);

    @Override
    List<Turns> findAll();
}