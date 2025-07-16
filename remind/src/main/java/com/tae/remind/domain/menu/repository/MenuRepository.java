package com.tae.remind.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tae.remind.domain.menu.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>{

}
