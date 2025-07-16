package com.tae.remind.domain.news.model;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeywordAnalsisDto {

    Set<String> originIdList;
    String keyword;
    Long count;
    String sentiment;
}
