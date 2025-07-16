package com.tae.remind.domain.news.service;

import java.util.List;

import com.tae.remind.domain.news.model.KeywordAnalsisDto;
import com.tae.remind.domain.news.model.NewsDto;
import com.tae.remind.domain.news.model.SentimentDto;
import com.tae.remind.domain.news.model.keywordDto;

public interface NewsService {

    public List<NewsDto> getNewsData(String keyword, int count);

    public SentimentDto getKeywordInfo(String linkUrl,String description);

    public List<keywordDto> getKeywordInfoList(List<NewsDto> newsList);

    public List<KeywordAnalsisDto> getKeywordAnalsis(List<NewsDto> newsList);
}
