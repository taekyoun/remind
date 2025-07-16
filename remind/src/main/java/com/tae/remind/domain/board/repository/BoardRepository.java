package com.tae.remind.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tae.remind.domain.board.model.BoardData;

@Repository
public interface BoardRepository extends JpaRepository<BoardData,Integer>{

}
