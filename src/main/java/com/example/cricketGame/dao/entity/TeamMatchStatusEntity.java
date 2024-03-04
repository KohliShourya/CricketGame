package com.example.cricketGame.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TeamMatchStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer matchId;
    private String teamName;
    private int totalScore;
    private int numberOfWicketsTaken;
    private int totalOversPlayed;
}

