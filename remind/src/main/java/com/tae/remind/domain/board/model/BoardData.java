package com.tae.remind.domain.board.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class BoardData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer no;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false)
    private LocalDateTime writeDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardType type;
}
