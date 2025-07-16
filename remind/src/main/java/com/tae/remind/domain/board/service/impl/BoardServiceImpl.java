package com.tae.remind.domain.board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tae.remind.domain.board.model.BoardDto;
import com.tae.remind.domain.board.model.BoardMapper;
import com.tae.remind.domain.board.repository.BoardRepository;
import com.tae.remind.domain.board.service.BoardService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    @Override
    public List<BoardDto> getBoartDataList() {
        return boardMapper.toDtoList(boardRepository.findAll());
    }

    @Override
    public BoardDto getBoartDataOne(Integer no) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoartDataOne'");
    }

    @Override
    public Integer createBoardData(BoardDto boardDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBoardData'");
    }

    @Override
    public Boolean updateBoardData(BoardDto boardDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBoardData'");
    }

    @Override
    public Boolean deleteBoardData(Integer no) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBoardData'");
    }

}
