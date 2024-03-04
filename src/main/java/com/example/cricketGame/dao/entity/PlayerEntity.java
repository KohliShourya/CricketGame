package com.example.cricketGame.dao.entity;

import com.example.cricketGame.model.PlayerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="players")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String teamName;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private PlayerType playerType;
}
