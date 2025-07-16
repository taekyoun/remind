package com.tae.remind.domain.menu.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tae.remind.domain.menu.model.Menu;
import com.tae.remind.domain.menu.model.MenuDto;
import com.tae.remind.domain.menu.model.MenuMapper;
import com.tae.remind.domain.menu.repository.MenuRepository;
import com.tae.remind.domain.menu.service.MenuService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;
    private final MenuHandler menuHandler;
    private final MenuMapper menuMapper;

    @Override
    public List<MenuDto> getMenuList(String sort) {
    List<MenuDto> topMenus = new ArrayList<>();
        switch (sort) {
            case "all":
                topMenus = menuHandler.getMenuCache().stream()
                    .map(menuMapper::menuToMenuDto)
                    .filter(dto-> (dto.getUpperMenu()==null|| dto.getUpperMenu().isBlank()))
                    .sorted(Comparator.comparingInt(MenuDto::getOrder))
                    .toList();
                break;
            case "use":
                topMenus = menuHandler.getMenuCache().stream()
                .map(menuMapper::menuToMenuDto)
                .filter(MenuDto::getUsage)
                .filter(dto-> (dto.getUpperMenu()==null|| dto.getUpperMenu().isBlank()))
                .sorted(Comparator.comparingInt(MenuDto::getOrder))
                .toList();
                break;
            default:
                topMenus = menuHandler.getMenuCache().stream()
                .map(menuMapper::menuToMenuDto)
                .filter(dto-> (dto.getUpperMenu()==null|| dto.getUpperMenu().isBlank()))
                .sorted(Comparator.comparingInt(MenuDto::getOrder))
                .toList();
        }
        List<MenuDto> hierarchyMenuList = new ArrayList<>();
        topMenus.forEach(menu->{
            hierarchyMenuList.add(recursiveMenus(menu));
        });
 
        return hierarchyMenuList;
    }

    @Override
    public void insertMenu(MenuDto menuDto) {
        menuRepository.save(menuMapper.menuDtoToMenu(menuDto));
        menuHandler.refreshMenuData();
    }

    @Override
    @Transactional
    public void updateMenu(MenuDto menuDto) {
        Menu menu =menuRepository.findById(menuMapper.menuDtoToMenu(menuDto).getId()).orElseThrow(()->new EntityNotFoundException());
        menu.setName(menuDto.getName());
        menu.setComment(menuDto.getComment());
        menu.setPath(menuDto.getPath());
        menu.setUpperMenu(menuDto.getUpperMenu());
        menu.setUsage(menuDto.getUsage());
        menu.setOrder(menuDto.getOrder());
        menuRepository.save(menu);
        menuHandler.refreshMenuData();
    }

    @Override
    public void deleteMenu(Integer id) {
        menuRepository.deleteById(id);
        menuHandler.refreshMenuData();
    }

    private MenuDto recursiveMenus(MenuDto menuDto){
        List<MenuDto> subMenus = menuHandler.getMenuCache().stream()
            .map(menuMapper::menuToMenuDto)
            .filter(dto->(dto.getUpperMenu()!=null && !dto.getUpperMenu().isBlank()))
            .filter(dto->dto.getUpperMenu().equals(menuDto.getName()))
            .sorted(Comparator.comparingInt(MenuDto::getOrder))
            .toList();
        if(subMenus.size()>0){
            List<MenuDto> subMenuDtos = new ArrayList<>();
            subMenus.forEach(menu->{
                subMenuDtos.add(recursiveMenus(menu));
            });
            menuDto.setSubMenu(subMenuDtos);
            return menuDto;
        }
        else{
            menuDto.setSubMenu(null);
            return menuDto;
        }

    }

}
