package com.example.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebCrawlerService {

    public List<String> crawl(String url) throws IOException {
        List<String> result = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();

        // Extract title
        result.add("Title: " + doc.title());

        // Extract all links
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            result.add("Link: " + link.attr("abs:href") + " | Text: " + link.text());
        }

        return result;
    }
}
