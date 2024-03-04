package com.example.cricketGame.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TossParams implements Serializable {
    String teamA;
    String teamB;
    Integer overs;
}
