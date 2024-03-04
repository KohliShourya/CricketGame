package com.example.cricketGame.utils.service;

import com.example.cricketGame.dao.Repository.PlayerRepository;
import com.example.cricketGame.dao.entity.PlayerEntity;
import com.example.cricketGame.model.PlayerBattingStats;
import com.example.cricketGame.model.PlayerBowlingStats;
import com.example.cricketGame.model.PlayerType;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class CricketGameServiceUtils {
    public static PlayerRepository playerRepository;

    public CricketGameServiceUtils(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public static String getBattingTeam(String teamA, String teamB) {
        Random random = new Random();
        int randomNumber = random.nextInt(2); // Generates 0 or 1
        if (randomNumber == 0) {
            return teamA;
        } else {
            return teamB;
        }
    }

    public static List<PlayerEntity> createPlayersAList(String teamName) {
        List<PlayerEntity> players = new ArrayList<>();

        // Add 10 players to the list
        for (int i = 1; i <= 11; i++) {
            PlayerType type = (i % 2 == 0) ? PlayerType.BATSMAN : PlayerType.BOWLER; // Alternate between batsman and bowler
            PlayerEntity player = new PlayerEntity(i, teamName,"Player" + i,20+i,type);
            players.add(player);
        }
        playerRepository.saveAll(players); //TODO : Remove this once data is persisted
        return players;
    }

    public static List<PlayerEntity> createPlayersBList(String teamName) {
        List<PlayerEntity> players = new ArrayList<>();

        // Add 10 players to the list
        for (int i = 12; i <= 22; i++) {
            PlayerType type = (i % 2 == 0) ? PlayerType.BATSMAN : PlayerType.BOWLER; // Alternate between batsman and bowler
            PlayerEntity player = new PlayerEntity(i, teamName, "Player" + i, 20 + i, type);
            players.add(player);
        }
        playerRepository.saveAll(players);  //TODO : Remove this once data is persisted
        return players;
    }

    public static int generateRandomScore() {
        Random random = new Random();
        return random.nextInt(8) - 1; // Generates a random number between 0 (inclusive) and 8 (exclusive), then subtracts 1
    }

    public static PlayerEntity getNextBatsMen(List<PlayerEntity> players, Set<Integer> battedPlayers) {
        Random random = new Random();
        PlayerEntity selectedPlayer;
        do {
            int index = random.nextInt(players.size());
            selectedPlayer = players.get(index);
        } while (battedPlayers.contains(selectedPlayer.getId()));

        battedPlayers.add(selectedPlayer.getId());
        return selectedPlayer;
    }

    public static PlayerEntity getNextBowler(List<PlayerEntity> players, int previousBowlerId) {
        Random random = new Random();
        PlayerEntity selectedPlayer;
        do {
            int index = random.nextInt(players.size());
            selectedPlayer = players.get(index);
        } while (selectedPlayer.getId() == previousBowlerId);

        return selectedPlayer;
    }
    public static Map<Integer, PlayerBattingStats> createPlayerMatchBattingStats(Integer matchId, List<PlayerEntity> players) {
        Map<Integer, PlayerBattingStats> battingStats = new HashMap<>();
        players.forEach((player) -> {
            battingStats.put(player.getId(), new PlayerBattingStats(matchId, player.getId()));
        });
        return battingStats;
    }

    public static Map<Integer, PlayerBowlingStats> createPlayerMatchBowlingStats(Integer matchId, List<PlayerEntity> players) {
        Map<Integer, PlayerBowlingStats> bowlingStats = new TreeMap<>();
        players.forEach((player) -> {
            bowlingStats.put(player.getId(), new PlayerBowlingStats(matchId, player));
        });
        return bowlingStats;
    }
}
