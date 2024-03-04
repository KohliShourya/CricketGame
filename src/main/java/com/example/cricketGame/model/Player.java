package com.example.cricketGame.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Player implements Serializable {
    Integer id;
    String teamName;
    String name;
    Integer age;
    PlayerType type;
}
