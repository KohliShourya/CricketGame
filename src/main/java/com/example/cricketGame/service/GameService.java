package com.example.cricketGame.service;

import com.example.cricketGame.model.response.TossResponse;

public interface GameService {
    TossResponse doToss(String teamA, String teamB, Integer overs);
    String startGame(Integer matchId);

}
