package com.tae.remind.domain.news.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tae.remind.domain.news.model.KeywordAnalsisDto;
import com.tae.remind.domain.news.model.NewsDto;
import com.tae.remind.domain.news.model.SentimentDto;
import com.tae.remind.domain.news.model.keywordDto;
import com.tae.remind.domain.news.repository.WordSentimentDictionaryRepository;
import com.tae.remind.domain.news.service.NewsService;
import com.tae.remind.domain.news.utillity.KeywordAnalyzer;
import com.tae.remind.domain.news.utillity.WebCrawling;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService{

    private final WordSentimentDictionaryRepository dictionaryRepository;
    private final WebCrawling<NewsDto> webCrawling;
    private final KeywordAnalyzer keywordAnalyzer;

  

    @Override
    @Cacheable("newsData")
    public List<NewsDto> getNewsData(String keyword, int count) {
        final String CLIENT_ID = "OgdTH1QY4Kc60YKWbcT8"; 
        final String CLIENT_SECRET = "kiw0iy5mYN"; 
        List<NewsDto> newList = new ArrayList<>();
        String text = null;

         try {
            text = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiUrl ="https://openapi.naver.com/v1/search/news.json?sort=date&start=1&query=" + text + "&display="+count;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);
        request.addHeader("X-Naver-Client-Id", CLIENT_ID);
        request.addHeader("X-Naver-Client-Secret", CLIENT_SECRET);

        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
                JsonArray items = jsonObject.getAsJsonArray("items");

                for (int i = 0; i < items.size(); i++) {
                    JsonObject item = items.get(i).getAsJsonObject();
                    String title = item.get("title").getAsString();
                    String link = item.get("link").getAsString();
                    String description = item.get("description").getAsString();
                    String pubDate = item.get("pubDate").getAsString();

                    newList.add(NewsDto.builder()
                                    .title(title)
                                    .description(description)
                                    .linkUrl(link)
                                    .date(pubDate)
                                    .id(""+i)
                                    .build());
                }
            }
        }
        catch(IOException e){
            System.out.println("Error" + e);
        }
        return newList;
    }

    @Override
    @Transactional
    @Cacheable("keywordData")
    public List<keywordDto> getKeywordInfoList(List<NewsDto> newsList) {
        HashMap<String,String> wordSentimentMap = new HashMap<>();
        dictionaryRepository.findAll().forEach(wordSentiment->{
            wordSentimentMap.put(wordSentiment.getWord(), wordSentiment.getSentiment());
        });
        List<CompletableFuture<List<keywordDto>>> futures = newsList.stream()
            .map(dto -> webCrawling.crawlNews(dto.getLinkUrl(), dto) 
                .thenCompose(newsDto -> 
                    keywordAnalyzer.analyzeContent(newsDto.getContent())
                        .thenApply(keywordMap -> 
                            keywordMap.entrySet().stream()
                                .map(entry -> keywordDto.builder()
                                    .originId(newsDto.getId())
                                    .keyword(entry.getKey())
                                    .count(entry.getValue())
                                    .build())
                                .collect(Collectors.toList())
                        )
                ))
            .collect(Collectors.toList());

        return futures.parallelStream()
            .flatMap(future -> {
                try {
                    return future.get().stream();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .map(keywordDto -> {
                String sentimentCode = wordSentimentMap.getOrDefault(keywordDto.getKeyword(), "keyword");
                switch (sentimentCode) {
                    case "DIC02":
                        keywordDto.setSentiment("긍정");
                        break;
                    case "DIC03":
                        keywordDto.setSentiment("부정");
                        break;
                    default:
                        keywordDto.setSentiment(sentimentCode);
                        break;
                }
                return keywordDto;
            })
            .filter(dto -> !"DIC01".equals(dto.getSentiment()))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @Cacheable("keywordData")
    public List<KeywordAnalsisDto> getKeywordAnalsis(List<NewsDto> newsList) {
        List<keywordDto> keywordList = getKeywordInfoList(newsList);
        Map<String,List<keywordDto>> groupKeyword = keywordList.stream().collect(Collectors.groupingBy(keywordDto::getKeyword));
        //Map<String,Integer> keywordCntMap = new HashMap<>();
        List<KeywordAnalsisDto> keywordAnalsisList = new ArrayList<>();
        groupKeyword.forEach((key,list)->{
            Long cnt =0L;
            Set<String> originIdList = new HashSet<>();
            for(keywordDto dto :list){
                cnt +=dto.getCount();
                originIdList.add(dto.getOriginId());
            }
            keywordAnalsisList.add(
                KeywordAnalsisDto.builder()
                    .originIdList(originIdList)
                    .keyword(key)
                    .count(cnt)
                    .sentiment(list.get(0).getSentiment())
                    .build());
            //keywordCntMap.put(key, cnt);
        });
        return keywordAnalsisList;
    }

    @Override
    public SentimentDto getKeywordInfo(String linkUrl,String description) {
        int[] countArr = new int[3];
        HashMap<String,String> wordSentimentMap = new HashMap<>();
        dictionaryRepository.findAll().forEach(wordSentiment->{
            wordSentimentMap.put(wordSentiment.getWord(), wordSentiment.getSentiment());
        });
        String html =webCrawling.fetchContent(linkUrl);
        if(html.length()==0){
            html = description;
        }
        keywordAnalyzer.analyze(html).entrySet()
            .stream()
            .map(entry -> keywordDto.builder()
                .keyword(entry.getKey())
                .count(entry.getValue())
                .build())
            .map(keywordDto -> {
                keywordDto.setSentiment(wordSentimentMap.getOrDefault(keywordDto.getKeyword(), "keyword"));
                return keywordDto;
            })
            .filter(keywordDto -> !"DIC01".equals(keywordDto.getSentiment()))
            .forEach(keywordDto->{
                if(keywordDto.getSentiment().equals("DIC02")) countArr[0]++;
                else if(keywordDto.getSentiment().equals("DIC03")) countArr[1]++;
                else countArr[2]++;
            });
            
        return SentimentDto.builder()
            .positiveCnt(countArr[0])
            .negativeCnt(countArr[1])
            .restCnt(countArr[2])
            .build();
    }  
}
