package com.itb.itsmyturn.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "subjects", schema = "itsmyturn")
@Entity
public class Subjects implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @Column(name = "idteacher", nullable = false)
    private Integer idTeacher;

    @Column(name = "subjectname", nullable = false)
    private String subjectName;

    @Column(name = "usergroup", nullable = false)
    private String userGroup;

    
}