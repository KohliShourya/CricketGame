package com.example.cricketGame.dao.Repository;

import com.example.cricketGame.dao.entity.BowlingStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BowlingStatsRepository extends JpaRepository<BowlingStatsEntity,Long> {
    BowlingStatsEntity findByPlayerIdAndMatchId(Integer playerId, Integer matchId);
}
