package com.tae.remind.domain.board.service;

import java.util.List;

import com.tae.remind.domain.board.model.BoardDto;

public interface BoardService {

    public List<BoardDto> getBoartDataList();
    
    public BoardDto getBoartDataOne(Integer no);
    
    public Integer createBoardData(BoardDto boardDto);

    public Boolean updateBoardData(BoardDto boardDto);

    public Boolean deleteBoardData(Integer no);
}
