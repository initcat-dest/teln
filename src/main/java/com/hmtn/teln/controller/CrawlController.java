package com.hmtn.teln.controller;

import com.hmtn.teln.service.ThsGnCrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * class description TODO
 *
 * @author libo
 * @package com.hmtn.teln.controller
 * @company initcat
 * @date 2020/11/1
 */
@Controller
public class CrawlController {

    @Autowired
    private ThsGnCrawlService thsGnCrawlService;

    @RequestMapping("/test")
    @ResponseBody
    public void test() {
        List<HashMap<String, String>> list = thsGnCrawlService.ThsGnCrawlListUrl();
    }
}
