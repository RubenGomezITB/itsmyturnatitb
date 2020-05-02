package com.itb.itsmyturn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "webuser")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Webuser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private String rol;
}