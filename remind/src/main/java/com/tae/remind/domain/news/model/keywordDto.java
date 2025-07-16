package com.tae.remind.domain.news.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class keywordDto {

    String originId;
    String keyword;
    Long count;
    @Builder.Default
    String sentiment = "common";

}
