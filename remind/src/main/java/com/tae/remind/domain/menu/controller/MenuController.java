package com.tae.remind.domain.menu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tae.remind.domain.menu.model.MenuDto;
import com.tae.remind.domain.menu.service.MenuService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

   private final MenuService menuService;

    @GetMapping("/{sort}")
    public ResponseEntity<List<MenuDto>> getMenu(@PathVariable String sort){
        return ResponseEntity.ok(menuService.getMenuList(sort));
    }

    @PostMapping
    public ResponseEntity<String> insertMenu(@RequestBody MenuDto menuDto){
        try {
            menuService.insertMenu(menuDto); 
            return ResponseEntity.ok("Item inserted successfully"); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert item"); 
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMenu(@RequestBody MenuDto menuDto){
        try {
            menuService.updateMenu(menuDto); 
            return ResponseEntity.ok("Item updateded successfully"); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update item"); 
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Integer id){
        try {
            menuService.deleteMenu(id); 
            return ResponseEntity.ok("Item updateded successfully"); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update item"); 
        }
    }
}