package com.example.cricketGame.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BowlingStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer matchId;
    private Integer playerId;
    private Integer fours;
    private Integer sixes;
    private Integer ballsBowled;
    private Integer runsGained;
    private Integer wicketsTaken;
}
