package com.tae.remind.domain.news.utillity;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;

@Component
public class KeywordAnalyzer {

    private final Komoran komoran;

    public KeywordAnalyzer(Komoran komoran){
        this.komoran= komoran;
    }


    public Komoran tool(){
        return this.komoran;
    }

    public String textClean(String text){
        return text.replaceAll("[^가-힣a-zA-Z0-9\\s]+|\\s+", " ").trim();
    }

    public Map<String,Long> analyze(String text){
        return this.komoran
            .analyze(textClean(text))
            .getTokenList()
            .stream()
            .filter(token -> "NNG".equals(token.getPos()))
            .filter(token -> token.getMorph().length()>1)
            .collect(Collectors.groupingBy(Token::getMorph, Collectors.counting()));
            
    }

  
    @Retryable(
    value = { RejectedExecutionException.class },
    maxAttempts = 5,
    backoff = @Backoff(delay = 1000))
    @Async("taskExecutor")
    public CompletableFuture<Map<String, Long>> analyzeContent(String content) {
        Map<String, Long> keywords = analyze(content); // 형태소 분석 작업
        return CompletableFuture.completedFuture(keywords);
    }
}
