package com.tae.remind.domain.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tae.remind.domain.news.model.WordSentimentDictionary;

@Repository
public interface WordSentimentDictionaryRepository extends JpaRepository<WordSentimentDictionary, String>{

}
