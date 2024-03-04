package com.example.cricketGame.controller;

import com.example.cricketGame.model.request.TossParams;
import com.example.cricketGame.model.response.BaseResponse;
import com.example.cricketGame.model.response.TossResponse;
import com.example.cricketGame.service.GameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController implements BaseApi{

    private final GameServiceImpl cricketGameServiceImpl;

    public GameController(GameServiceImpl cricketGameServiceImpl) {
        this.cricketGameServiceImpl = cricketGameServiceImpl;
    }

    @PostMapping("/toss")
    ResponseEntity<BaseResponse<TossResponse>> toss(@RequestBody TossParams tossParams){
        var tossResponse = cricketGameServiceImpl.doToss(tossParams.getTeamA(),tossParams.getTeamB(),tossParams.getOvers());
        BaseResponse<TossResponse> baseResponse = new BaseResponse<>(tossResponse);
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/startMatch/{matchId}")
    ResponseEntity<BaseResponse<String>> startMatch(@PathVariable Integer matchId){
        String matchResponse = cricketGameServiceImpl.startGame(matchId);
        BaseResponse<String> response = new BaseResponse<>(matchResponse);
        return ResponseEntity.ok(response);
    }
}
