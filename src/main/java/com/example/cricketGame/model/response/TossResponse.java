package com.example.cricketGame.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TossResponse implements Serializable {
    Integer matchId;
    String battingTeam;
}
