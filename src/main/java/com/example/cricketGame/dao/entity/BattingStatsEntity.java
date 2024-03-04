package com.example.cricketGame.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattingStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    Integer matchId;
    Integer playerId;
    private int fours;
    private int sixes;
    private int ballsPlayed;
    private int totalRuns;
}
