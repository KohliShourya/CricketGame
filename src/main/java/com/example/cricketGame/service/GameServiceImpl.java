package com.example.cricketGame.service;

import com.example.cricketGame.constant.CommonConstants;
import com.example.cricketGame.dao.Repository.*;
import com.example.cricketGame.dao.entity.*;
import com.example.cricketGame.model.BallStats;
import com.example.cricketGame.model.PlayerBattingStats;
import com.example.cricketGame.model.PlayerBowlingStats;
import com.example.cricketGame.model.response.TossResponse;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.cricketGame.utils.service.CricketGameServiceUtils.*;

@Service
public class GameServiceImpl implements GameService {

    private final TeamMatchStatsRepository teamMatchStatusRepository;
    private final PlayerRepository playerRepository;
    private final ScoreCardRepository scoreCardRepository;
    private final MatchRepository matchRepository;
    private final BattingStatsRepository battingStatsRepository;
    private final BowlingStatsRepository bowlingStatsRepository;

    public GameServiceImpl(TeamMatchStatsRepository teamMatchStatusRepository, PlayerRepository playerRepository,
                           MatchRepository matchRepository,
                           ScoreCardRepository scoreCardRepository,
                           BattingStatsRepository battingStatsRepository, BowlingStatsRepository bowlingStatsRepository) {
        this.teamMatchStatusRepository = teamMatchStatusRepository;
        this.playerRepository = playerRepository;
        this.scoreCardRepository = scoreCardRepository;
        this.matchRepository = matchRepository;
        this.battingStatsRepository = battingStatsRepository;
        this.bowlingStatsRepository = bowlingStatsRepository;
    }

    public TossResponse doToss(String teamA, String teamB, Integer overs) {
        String battingTeam = getBattingTeam(teamA, teamB);
        MatchEntity matchEntity = MatchEntity.builder()
                .teamA(teamA)
                .teamB(teamB)
                .overs(overs)
                .build();
        var result = matchRepository.save(matchEntity);
        return new TossResponse(result.getId(), battingTeam);
    }

    public String startGame(Integer matchId) {
        MatchEntity matchEntity = matchRepository.getReferenceById(matchId);
        Integer totalOvers = matchEntity.getOvers();
//        List<PlayerEntity> teamAPlayers = playerRepository.findAllByTeamName(matchEntity.getTeamA());
//        List<PlayerEntity> teamBPlayers = playerRepository.findAllByTeamName(matchEntity.getTeamB());
        List<PlayerEntity> teamAPlayers = createPlayersAList(matchEntity.getTeamA());
        List<PlayerEntity> teamBPlayers = createPlayersAList(matchEntity.getTeamB());

        List<PlayerEntity> allPlayers = new ArrayList<>(teamAPlayers);
        allPlayers.addAll(teamBPlayers);
        Map<Integer, PlayerBattingStats> battingStats = createPlayerMatchBattingStats(matchId, allPlayers);
        Map<Integer, PlayerBowlingStats> bowlingStats = createPlayerMatchBowlingStats(matchId, allPlayers);

        int targetRuns = startInnings(matchId, totalOvers, teamAPlayers, teamBPlayers, battingStats, bowlingStats,-1);
        int achievedRuns = startInnings(matchId, totalOvers, teamBPlayers, teamAPlayers, battingStats, bowlingStats,targetRuns);
        persistMatchBattingStats(matchId, battingStats);
        persistMatchBowlingStats(matchId, bowlingStats);

        String matchResult = CommonConstants.DRAW_MATCH_MESSAGE;
        if(achievedRuns != targetRuns) {
            if (achievedRuns > targetRuns) {
                matchRepository.updateWinningTeamStatus(matchId, matchEntity.getTeamA());
                matchResult = String.format(CommonConstants.WINNING_MATCH_MESSAGE, matchEntity.getTeamA());
            } else {
                matchRepository.updateWinningTeamStatus(matchId, matchEntity.getTeamB());
                matchResult = String.format(CommonConstants.WINNING_MATCH_MESSAGE, matchEntity.getTeamB());
            }
        }
        return matchResult;
    }


    private int startInnings(Integer matchId, Integer totalOvers,
                     List<PlayerEntity> battingTeam,
                     List<PlayerEntity> bowlingTeam,
                     Map<Integer, PlayerBattingStats> battingStats,
                     Map<Integer, PlayerBowlingStats> bowlingStats,
                     Integer targetRuns) {
        int wickets = 0, totalRuns = 0, previousBowlerId = -1;
        Set<Integer> hasBatted = new HashSet<>();
        PlayerEntity currentBatsman = getNextBatsMen(battingTeam, hasBatted);
        PlayerEntity standingBatsman = getNextBatsMen(battingTeam, hasBatted);

        for (int overNumber = 1; overNumber <= totalOvers; overNumber++) {
            PlayerEntity currentBowler = getNextBowler(bowlingTeam, previousBowlerId);

            for (int ball = 1; ball <= 6; ball++) {
                int runScored = generateRandomScore();
                boolean isWicket = false;
                BallStats ballStats = getBallStats(matchId, ball, overNumber, runScored, currentBatsman, currentBowler, battingStats, bowlingStats);
                if (runScored >= 0) {
                    totalRuns += runScored;
                    updateBallStats(ballStats);
                    // Switch batsmen
                    if (runScored % 2 != 0) {
                        PlayerEntity temp = currentBatsman;
                        currentBatsman = standingBatsman;
                        standingBatsman = temp;
                    }
                } else {
                    isWicket = true;
                    wickets++;
                    updateBallStats(ballStats);
                }
                if (isWicket) {
                    if (wickets == 8)
                        return totalRuns;
                    currentBatsman = getNextBatsMen(battingTeam, hasBatted);
                }
            }
            if(targetRuns!=-1 && totalRuns > targetRuns)
                return totalRuns;
            // Switch batsmen for nextOver
            PlayerEntity temp = currentBatsman;
            currentBatsman = standingBatsman;
            standingBatsman = temp;
            previousBowlerId = currentBowler.getId();
        }
        return totalRuns;
    }

    private BallStats getBallStats(Integer matchId, int ball, int overNumber, int runScored, PlayerEntity currentBatsman, PlayerEntity currentBowler, Map<Integer, PlayerBattingStats> battingStats, Map<Integer, PlayerBowlingStats> bowlingStats) {
        return BallStats.builder()
                .matchId(matchId)
                .ball(ball)
                .overNumber(overNumber)
                .runScored(runScored)
                .currentBatsman(currentBatsman.getId())
                .currentBowler(currentBowler.getId())
                .battingStats(battingStats)
                .bowlingStats(bowlingStats)
                .build();
    }

    private void persistMatchBowlingStats(Integer matchId, Map<Integer, PlayerBowlingStats> bowlingStats) {
        Optional<MatchEntity> match = matchRepository.findById(matchId);
        List<BowlingStatsEntity> bowlingStatsEntities = bowlingStats.values()
                .stream()
                .map((bowlingStat) ->
                        BowlingStatsEntity
                                .builder()
                                .playerId(bowlingStat.getPlayerId())
                                .matchId(matchId)
                                .sixes(bowlingStat.getSixes())
                                .fours(bowlingStat.getFours())
                                .runsGained(bowlingStat.getTotalRuns())
                                .wicketsTaken(bowlingStat.getWicketsTaken())
                                .ballsBowled(bowlingStat.getBallsBowled())
                                .build())
                .toList();
        bowlingStatsRepository.saveAll(bowlingStatsEntities);
    }

    private void persistMatchBattingStats(Integer matchId, Map<Integer, PlayerBattingStats> battingStats) {
        Optional<MatchEntity> match = matchRepository.findById(matchId);
        List<BattingStatsEntity> battingStatsEntities = battingStats.values()
                .stream()
                .map((battingStat) ->
                        BattingStatsEntity
                                .builder()
                                .playerId(battingStat.getPlayerId())
                                .matchId(matchId)
                                .sixes(battingStat.getSixes())
                                .fours(battingStat.getFours())
                                .totalRuns(battingStat.getTotalRuns())
                                .ballsPlayed(battingStat.getBallsPlayed())
                                .build())
                .toList();
        battingStatsRepository.saveAll(battingStatsEntities);
    }


    private void updateBallStats(BallStats ballStats) {
        updateMatchScoreCard(ballStats.getMatchId(), ballStats.getOverNumber(), ballStats.getBall(), ballStats.getRunScored(), ballStats.getCurrentBatsman(), ballStats.getCurrentBowler());
        updateBattingStats(ballStats.getCurrentBatsman(), ballStats.getRunScored(), ballStats.getBattingStats());
        updateBowlingStats(ballStats.getCurrentBowler(), ballStats.getRunScored(), ballStats.getBowlingStats());
    }

    private void updateBattingStats(Integer playerId, int runs, Map<Integer, PlayerBattingStats> battingStats) {
        PlayerBattingStats playerStats = battingStats.get(playerId);
        playerStats.incrementRunsScored(runs);
        playerStats.incrementBallsPlayed();
    }

    private void updateBowlingStats(Integer playerId, int runs, Map<Integer, PlayerBowlingStats> bowlingStats) {
        PlayerBowlingStats playerStats = bowlingStats.get(playerId);
        playerStats.incrementRunsGained(runs);
        playerStats.incrementBallsBowled();
    }

    private void updateMatchScoreCard(int matchId, int over, int ballNumber, int runScored, Integer batsManId, Integer bowlerId) {
        ScoreCardEntity scoreCardEntity = ScoreCardEntity
                .builder()
                .matchId(matchId)
                .overNumber(over)
                .ballNumber(ballNumber)
                .batsmenId(batsManId)
                .bowlerId(bowlerId)
                .runs(runScored)
                .build();
        scoreCardRepository.save(scoreCardEntity);
    }


}
