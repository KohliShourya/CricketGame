package com.example.cricketGame.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class MatchResult implements Serializable {
    Integer matchId;
    String winningTeam;
}
