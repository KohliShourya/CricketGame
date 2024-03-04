package com.example.cricketGame.model;

import lombok.Data;

@Data
public class PlayerBattingStats extends PlayerMatchStats {
    int ballsPlayed;

    public PlayerBattingStats(Integer playerId,Integer matchId,Integer fours,Integer sixes ,Integer totalRuns,Integer ballsPlayed){
        super(playerId,matchId,fours,sixes,totalRuns);
        this.ballsPlayed = ballsPlayed;
    }
    public PlayerBattingStats(int matchId,int playerId){
        this.matchId = matchId;
        this.playerId = playerId;
    }
    public void incrementRunsScored(int runs){
        if(runs==-1)
                return;
        this.totalRuns += runs;
        switch(runs) {
            case 6:
                incrementSixes();
                break;
            case 4:
                incrementFours();
                break;
        }
    }

    public void incrementBallsPlayed() {
        this.ballsPlayed+=1;
    }

    private void incrementFours() {
        this.fours+=1;
    }

    private void incrementSixes() {
        this.sixes +=1;
    }
}
