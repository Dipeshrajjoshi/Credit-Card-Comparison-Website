package com.example.demo.service;

import com.example.demo.model.BoyerMoore;
import com.example.demo.reader.ExcelReader;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PageRankService {

    private static final String EXCEL_FILE_PATH = "src/main/resources/Credit_Card_Details.csv";

    public List<Map.Entry<String, Integer>> rankPagesByKeywords(List<String> keywords) {
        Map<String, String> pageContents = ExcelReader.readCreditCardContent(EXCEL_FILE_PATH);
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (Map.Entry<String, String> entry : pageContents.entrySet()) {
            String url = entry.getKey();
            String content = entry.getValue().toLowerCase();
            int totalKeywordCount = 0;

            for (String keyword : keywords) {
                BoyerMoore bm = new BoyerMoore(keyword.toLowerCase());
                totalKeywordCount += bm.count(content);
            }

            frequencyMap.put(url, totalKeywordCount);
        }

        List<Map.Entry<String, Integer>> ranked = new ArrayList<>(frequencyMap.entrySet());
        ranked.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return ranked;
    }
}
