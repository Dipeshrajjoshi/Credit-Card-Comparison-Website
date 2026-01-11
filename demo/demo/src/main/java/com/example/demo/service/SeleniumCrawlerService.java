package com.example.demo.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeleniumCrawlerService {

    public Set<String> crawlWebsite(String url, String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        Set<String> links = new HashSet<>();

        try {
            driver.get(url);
            Thread.sleep(3000); // Wait for JS to load

            List<WebElement> anchorTags = driver.findElements(By.tagName("a"));
            for (WebElement anchor : anchorTags) {
                String href = anchor.getAttribute("href");
                if (href != null && href.startsWith("http") && href.contains(url)) {
                    links.add(href);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return links;
    }
}
