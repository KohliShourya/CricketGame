package com.example.cricketGame.dao.Repository;

import com.example.cricketGame.dao.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity,Integer> {
    List<PlayerEntity> findAllByTeamName(String teamName);
}
