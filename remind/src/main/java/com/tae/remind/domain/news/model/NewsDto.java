package com.tae.remind.domain.news.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDto {

    private String id;
    private String title;
    private String description;
    private String content;
    private String linkUrl;
    private String date;
}
