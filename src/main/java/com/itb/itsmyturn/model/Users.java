package com.itb.itsmyturn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "users", schema = "itsmyturn")
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isteacher", nullable = false)
    private boolean isteacher = false;

    public Users(String name, String email, String password, boolean isteacher) {
        name = this.name;
        email = this.email;
        password = this.password;
        isteacher = this.isteacher;
    }

    public boolean isTeacher() {
        return isteacher;
    }

    public void setTeacher(boolean teacher) {
        isteacher = teacher;
    }
}