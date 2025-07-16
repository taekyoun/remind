package com.tae.remind.domain.news.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentimentDto {

    private int keywordCnt;
    private int positiveCnt;
    private int negativeCnt;
    private int restCnt;


}
