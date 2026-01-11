package com.example.demo.controller;

import com.example.demo.service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/crawler")
@CrossOrigin(origins = "*")  // for frontend access
public class CrawlerController {

    @Autowired
    private WebCrawlerService crawlerService;

    @GetMapping
    public List<String> crawlWebsite(@RequestParam String url) throws IOException {
        return crawlerService.crawl(url);
    }
}
