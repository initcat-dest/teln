package com.hmtn.teln.service.impl;

import com.hmtn.teln.service.ThsGnCrawlService;
import com.hmtn.teln.service.ThsParseHtmlService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ThsGnCrawlServiceImpl implements ThsGnCrawlService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ThsGnCrawlServiceImpl.class);

    private final static String GN_URL = "http://www.114best.com/114.aspx?w=卫生院&pg=1";

    @Autowired
    private ThsParseHtmlService thsParseHtmlService;

    @Override
    public List<HashMap<String, String>> ThsGnCrawlListUrl() {
        System.setProperty("webdriver.chrome.driver", "/Users/libo/Downloads/chromedriver");
        ChromeOptions options = new ChromeOptions();
        //是否启用浏览器界面的参数
        //无界面参数
//        options.addArguments("headless");
        //禁用沙盒 就是被这个参数搞了一天
//        options.addArguments("no-sandbox");
        WebDriver webDriver = new ChromeDriver(options);
        try {
            // 根据网速设置，网速慢可以调低点
            webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            webDriver.get(GN_URL);
            Thread.sleep(1000L);
            String gnWindow = webDriver.getWindowHandle();
            String thsGnHtml = webDriver.getPageSource();
            List<HashMap<String, String>> hashMaps = thsParseHtmlService.parseGnHtmlReturnGnUrlList(thsGnHtml);
            for (HashMap<String, String> hashMap : hashMaps) {
                webDriver.get("http://www.114best.com" + hashMap.get("url"));
                Thread.sleep(2000L);
                gnWindow = webDriver.getWindowHandle();
                thsGnHtml = webDriver.getPageSource();

                HashMap<String, String> hashMaps1 = thsParseHtmlService.parseGnHtmlReturn(thsGnHtml);
                WebElement nextPageElement = webDriver.findElement(By.linkText("查看完整号码"));
                webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                nextPageElement.click();
                Thread.sleep(2000);
                String nextPageHtml = webDriver.getPageSource();
                thsParseHtmlService.parseFinalReturn(nextPageHtml);

            }
        } catch (Exception e) {
            LOGGER.error("获取同花顺概念页面的HTML，出现异常:", e);
        } finally {
            webDriver.close();
            webDriver.quit();
        }
        return null;
    }
}
