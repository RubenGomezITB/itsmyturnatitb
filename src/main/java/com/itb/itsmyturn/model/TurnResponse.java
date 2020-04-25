package com.itb.itsmyturn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TurnResponse implements Serializable {
    private int id;
    private String student;
    private String time;
    private String description;
}
