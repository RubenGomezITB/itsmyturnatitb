package com.itb.itsmyturn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "turns")
public class Turns implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @Column(name = "idstudent", nullable = false)
    private Integer idStudent;

    @Column(name = "idsubject", nullable = false)
    private Integer idSubject;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "description")
    private String description = "NULL";

    
}