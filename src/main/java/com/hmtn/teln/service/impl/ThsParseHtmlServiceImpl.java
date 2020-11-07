package com.hmtn.teln.service.impl;

import com.hmtn.teln.service.ThsParseHtmlService;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ThsParseHtmlServiceImpl implements ThsParseHtmlService {

    /**
     * 解析同花顺概念板块的Html页面：http://q.10jqka.com.cn/gn/
     * 返回所有概念板块的url地址
     */
    @Override
    public List<HashMap<String, String>> parseGnHtmlReturnGnUrlList(String html) {
        if (StringUtil.isBlank(html)) {
            return null;
        }
        List<HashMap<String, String>> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements cateItemsFromClass = document.getElementsByClass("search-result-company-name");
        for (Element element : cateItemsFromClass) {
            Elements as = element.getElementsByTag("a");
            for (Element a : as) {
                String gnUrl = a.attr("href");
                String name = a.text();
                HashMap<String, String> map = new HashMap<>();
                map.put("url", gnUrl);
                map.put("gnName", name);
                list.add(map);
            }
        }
        return list;
    }


    @Override
    public HashMap<String, String> parseGnHtmlReturn(String html) {
        if (StringUtil.isBlank(html)) {
            return null;
        }
        Document document = Jsoup.parse(html);
        Elements cateItemsFromClass = document.getElementsByClass("content");
        for (Element element : cateItemsFromClass) {
            Elements name = element.getElementsByAttributeStarting("id");
            HashMap<String, String> map = new HashMap<>();

            for (Element a : name) {
                String gnUrl = a.attr("id");
                String value = a.attr("value");
                if ("telToken".equals(gnUrl)) {
                    map.put("telToken", value);
                }
                if ("zID".equals(gnUrl)) {
                    map.put("zID", value);
                }
            }
            return map;
        }
        return null;
    }

    @Override
    public HashMap<String, String> parseFinalReturn(String html) {
        if (StringUtil.isBlank(html)) {
            return null;
        }
        System.out.println("-------------------------------------------------------");
        Document document = Jsoup.parse(html);
        Elements tb_tel2 = document.getElementsByClass("tb_tel2");
        Elements lis = tb_tel2.tagName("li");
        for (Element li : lis) {
            System.out.println(li.text());
        }
        Elements trs = document.select("table").select("tr");
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            for (Element td : tds) {
                System.out.println(td.text());
            }
        }
//        Elements cateItemsFromClass = document.getElementsByClass("tb_tel");
//        for (Element element : cateItemsFromClass) {
//            Elements name = element.getElementsByAttribute("td");
//            HashMap<String, String> map = new HashMap<>();
//
//            for (Element a : name) {
//                System.out.println(a.text());
//                String gnUrl = a.attr("id");
//                String value = a.attr("value");
//                if ("telToken".equals(gnUrl)) {
//                    map.put("telToken", value);
//                }
//                if ("zID".equals(gnUrl)) {
//                    map.put("zID", value);
//                }
//            }
//            return map;
//        }
        return null;
    }
}
