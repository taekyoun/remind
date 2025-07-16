package com.tae.remind.domain.menu.model;

import java.util.List;

import lombok.Data;

@Data
public class MenuDto {

    private Integer id;
    private String name;
    private String comment;
    private String upperMenu;
    private String path;
    private Boolean usage;
    private Integer order;
    private List<MenuDto> subMenu;
}
