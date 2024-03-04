package com.example.cricketGame.dao.Repository;

import com.example.cricketGame.dao.entity.ScoreCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreCardRepository extends JpaRepository<ScoreCardEntity, Long> {
}
