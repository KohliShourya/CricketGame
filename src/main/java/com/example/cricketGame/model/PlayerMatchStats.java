package com.example.cricketGame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMatchStats {
    Integer playerId;
    Integer matchId;
    Integer fours = 0;
    Integer sixes = 0;
    Integer totalRuns = 0;
}

