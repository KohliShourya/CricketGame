package com.example.cricketGame.dao.Repository;

import com.example.cricketGame.dao.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TeamRepository extends JpaRepository<TeamEntity,Integer> {
}
