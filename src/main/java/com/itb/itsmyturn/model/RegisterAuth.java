package com.itb.itsmyturn.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class RegisterAuth implements Serializable {
    String auth;
}
