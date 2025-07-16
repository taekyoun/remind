package com.tae.remind.domain.menu.service;

import java.util.List;

import com.tae.remind.domain.menu.model.MenuDto;

public interface MenuService {

    public List<MenuDto> getMenuList(String sort);

    public void insertMenu(MenuDto menuDto);

    public void updateMenu(MenuDto menuDto);

    public void deleteMenu(Integer id);
}
