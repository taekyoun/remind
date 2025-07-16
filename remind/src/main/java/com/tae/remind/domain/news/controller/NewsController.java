package com.tae.remind.domain.news.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tae.remind.domain.news.model.NewsDto;
import com.tae.remind.domain.news.service.NewsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/keyword")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    private enum SortType {
        news, info, once
    }

    @Data
    static class Params {

        @NotBlank
        private String keyword;

        @NotNull
        @Min(1)
        @Max(100)
        private int count;

        private String url;
        private String description;
    }
    

    @GetMapping("/{sort}")
    public ResponseEntity<Object> getContent(@PathVariable("sort") SortType sort,@Valid @ModelAttribute Params params){

        switch (sort) {
            case news:
                return ResponseEntity.ok(newsService.getNewsData(params.getKeyword(),params.getCount()));
            case info:
                List<NewsDto> newsList =newsService.getNewsData(params.getKeyword(),params.getCount());
                return ResponseEntity.ok(newsService.getKeywordAnalsis(newsList));
            case once:
                return ResponseEntity.ok(newsService.getKeywordInfo(params.getUrl(),params.getDescription()));
            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("Invalid sort parameter");
        }
    }
}
