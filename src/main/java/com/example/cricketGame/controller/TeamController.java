package com.example.cricketGame.controller;

import com.example.cricketGame.model.Player;
import com.example.cricketGame.model.PlayerBattingStats;
import com.example.cricketGame.model.PlayerBowlingStats;
import com.example.cricketGame.model.Team;
import com.example.cricketGame.model.response.BaseResponse;
import com.example.cricketGame.service.CricketTeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController implements BaseApi{
    private final CricketTeamService cricketTeamService;

    public TeamController(CricketTeamService cricketTeamService) {
        this.cricketTeamService = cricketTeamService;
    }
    @PostMapping("/createTeam")
    ResponseEntity<?> createTeam(@RequestBody Team team){
        cricketTeamService.createTeam(team);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addPlayer")
    ResponseEntity<?> addPlayerTeam(@RequestBody List<Player> players){
        cricketTeamService.addPlayer(players);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/teams")
    ResponseEntity<BaseResponse<List<Team>>> getAllTeams(){
        List<Team> teams = cricketTeamService.getAllTeams();
        var response = new BaseResponse<>(teams);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAllPlayers/{teamName}")
    ResponseEntity<?> addPlayerTeam(@PathVariable String teamName){
        List<Player> players = cricketTeamService.getPlayers(teamName);
        var response = new BaseResponse(players);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/player/{playerId}/match/{matchId}/battingstats")
    ResponseEntity<BaseResponse<PlayerBattingStats>> getPlayerMatchBattingStats(@PathVariable("playerId") Integer playerId, @PathVariable("matchId") Integer matchId){
        BaseResponse<PlayerBattingStats> battingStats = new BaseResponse<>(cricketTeamService.getBattingStatsForMatch(playerId,matchId));
        return ResponseEntity.ok(battingStats);
    }
    @GetMapping("/player/{playerId}/match/{matchId}/bowlingstats")
    ResponseEntity<BaseResponse<PlayerBowlingStats>> getPlayerMatchBowlingStats(@PathVariable("playerId") Integer playerId, @PathVariable("matchId") Integer matchId){
        BaseResponse<PlayerBowlingStats> bowlingStats = new BaseResponse<>(cricketTeamService.getBowlingStatsForMatch(playerId,matchId));
        return ResponseEntity.ok(bowlingStats);
    }

    @GetMapping("/team/{teamId}/match/{matchId}/teamStats")
    ResponseEntity<?> getTeamMatchStats(@PathVariable("teamId") Integer teamId,@PathVariable("matchId") Integer matchId){
        return ResponseEntity.ok().build();
    }

}
