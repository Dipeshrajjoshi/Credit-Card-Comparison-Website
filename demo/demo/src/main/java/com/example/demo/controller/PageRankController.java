package com.example.demo.controller;

import com.example.demo.service.PageRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagerank")
public class PageRankController {

    @Autowired
    private PageRankService pageRankService;

    @PostMapping
    public List<Map.Entry<String, Integer>> rankPages(@RequestBody List<String> keywords) {
        return pageRankService.rankPagesByKeywords(keywords);
    }
}
