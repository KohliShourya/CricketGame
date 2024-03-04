package com.example.cricketGame.dao.Repository;


import com.example.cricketGame.dao.entity.TeamMatchStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMatchStatsRepository extends JpaRepository<TeamMatchStatusEntity,Long> {
}
