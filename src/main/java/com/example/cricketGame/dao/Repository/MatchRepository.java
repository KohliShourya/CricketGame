package com.example.cricketGame.dao.Repository;

import com.example.cricketGame.dao.entity.MatchEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE MatchEntity e SET e.winningTeam = :winningTeam WHERE e.id = :id")
    void updateWinningTeamStatus(Integer id, String winningTeam);
}
