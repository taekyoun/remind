package com.tae.remind.domain.board.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardDto {

    private Integer no;
    private String title;
    private LocalDateTime writeDate;
    private BoardType type;
}
