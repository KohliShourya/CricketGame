package com.example.cricketGame.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class BallStats {
    Integer matchId; 
    Integer ball; 
    Integer overNumber; 
    Integer runScored; 
    Integer currentBatsman; 
    Integer currentBowler; 
    Map<Integer, PlayerBattingStats> battingStats;
    Map<Integer, PlayerBowlingStats> bowlingStats;
}
