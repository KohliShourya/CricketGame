package com.example.cricketGame.model;

import com.example.cricketGame.dao.entity.PlayerEntity;
import lombok.Data;

@Data
public class PlayerBowlingStats extends PlayerMatchStats {
    int wicketsTaken;
    int ballsBowled ;
    public PlayerBowlingStats(Integer playerId,Integer matchId,Integer fours,Integer sixes ,Integer totalRuns, Integer ballsBowled,Integer wicketsTaken){
        super(playerId,matchId,fours,sixes,totalRuns);
        this.ballsBowled = ballsBowled;
        this.wicketsTaken = wicketsTaken;
    }
    public PlayerBowlingStats(int matchId, PlayerEntity playerEntity){

        this.matchId = matchId;
        this.playerId = playerEntity.getId();
    }
    public void incrementRunsGained(int runs){
        this.totalRuns += runs;
        switch(runs) {
            case 6:
                incrementSixes();
                break;
            case 4:
                incrementFours();
                break;
            case -1:
                incrementWickets();
                break;
        }
    }

    public void incrementBallsBowled() {
        this.ballsBowled+=1;
    }
    private void incrementWickets() {
        this.wicketsTaken+=1;
    }

    private void incrementFours() {
        this.fours+=1;
    }

    private void incrementSixes() {
        this.sixes +=1;
    }
}
