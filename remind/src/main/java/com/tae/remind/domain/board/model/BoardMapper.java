package com.tae.remind.domain.board.model;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardDto toDto(BoardData boardData);
    List<BoardDto> toDtoList(List<BoardData>boardDataList);

}
