package com.example.cricketGame.service;

import com.example.cricketGame.dao.Repository.BattingStatsRepository;
import com.example.cricketGame.dao.Repository.BowlingStatsRepository;
import com.example.cricketGame.dao.Repository.PlayerRepository;
import com.example.cricketGame.dao.Repository.TeamRepository;
import com.example.cricketGame.dao.entity.BattingStatsEntity;
import com.example.cricketGame.dao.entity.BowlingStatsEntity;
import com.example.cricketGame.dao.entity.PlayerEntity;
import com.example.cricketGame.dao.entity.TeamEntity;
import com.example.cricketGame.model.PlayerBattingStats;
import com.example.cricketGame.model.Player;
import com.example.cricketGame.model.PlayerBowlingStats;
import com.example.cricketGame.model.Team;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CricketTeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final BattingStatsRepository battingStatsRepository;
    private final BowlingStatsRepository bowlingStatsRepository;

    public CricketTeamService(TeamRepository teamRepository, PlayerRepository playerRepository, BattingStatsRepository battingStatsRepository, BowlingStatsRepository bowlingStatsRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.battingStatsRepository = battingStatsRepository;
        this.bowlingStatsRepository = bowlingStatsRepository;
    }


    public void createTeam(Team team) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setName(team.getTeamName());
        teamRepository.save(teamEntity);
    }

    public void addPlayer(List<Player> players) {
        List<PlayerEntity> playerEntity = players.stream()
                .map(this::getPlayerEntity)
                .collect(Collectors.toList());
        playerRepository.saveAll(playerEntity);
    }

    private PlayerEntity getPlayerEntity(Player player) {
       return PlayerEntity.builder()
                .name(player.getName())
                .age(player.getAge())
                .teamName(player.getTeamName())
                .playerType(player.getType())
                .build();
    }
    public PlayerBattingStats getBattingStatsForMatch(Integer playerId, Integer matchId) {
        BattingStatsEntity battingStats = battingStatsRepository.findByPlayerIdAndMatchId(playerId,matchId);
        return new PlayerBattingStats(playerId,matchId,battingStats.getFours(),battingStats.getSixes(),battingStats.getTotalRuns(),battingStats.getBallsPlayed());
    }
    public PlayerBowlingStats getBowlingStatsForMatch(Integer playerId, Integer matchId) {
        BowlingStatsEntity bowlingStats = bowlingStatsRepository.findByPlayerIdAndMatchId(playerId,matchId);
        return new PlayerBowlingStats(playerId,matchId,bowlingStats.getFours(),bowlingStats.getSixes(),bowlingStats.getRunsGained(),bowlingStats.getBallsBowled(),bowlingStats.getWicketsTaken());
    }

    public List<Player> getPlayers(String teamName) {
        List<PlayerEntity> playerEntities = playerRepository.findAllByTeamName(teamName);
        return playerEntities.stream().map(playerEntity -> new
                Player(playerEntity.getId(),
                playerEntity.getTeamName(),
                playerEntity.getName(),
                playerEntity.getAge(),
                playerEntity.getPlayerType()
             ))
                .collect(Collectors.toList());
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll()
                .stream().map(teamEntity -> new Team(teamEntity.getName()))
                        .collect(Collectors.toList());
    }
}
