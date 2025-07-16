package com.tae.remind.domain.menu.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    public Menu menuDtoToMenu(MenuDto menuDto);

    @Mapping(target = "subMenu",ignore = true)
    public MenuDto menuToMenuDto(Menu menu);
}
