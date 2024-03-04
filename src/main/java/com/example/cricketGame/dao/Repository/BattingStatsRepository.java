package com.example.cricketGame.dao.Repository;

import com.example.cricketGame.dao.entity.BattingStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattingStatsRepository extends JpaRepository<BattingStatsEntity,Long> {
    BattingStatsEntity findByPlayerIdAndMatchId(Integer playerId, Integer matchId);
}
