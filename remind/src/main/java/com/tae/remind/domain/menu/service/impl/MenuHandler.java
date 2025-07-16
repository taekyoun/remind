package com.tae.remind.domain.menu.service.impl;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tae.remind.domain.menu.model.Menu;
import com.tae.remind.domain.menu.repository.MenuRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuHandler {

    private final MenuRepository menuRepository;
    private volatile List<Menu> menuCache;

    @PostConstruct
    public void init() {
        loadMenuData();
       
    }

    public void loadMenuData() {
        menuCache = menuRepository.findAll(); // 모든 메뉴를 로드
    }

    public List<Menu> getMenuCache() {
        return menuCache;
    }

    @Scheduled(fixedRate = 60000) // 60초마다 갱신
    public void refreshMenuData() {
        loadMenuData();
    }
}
