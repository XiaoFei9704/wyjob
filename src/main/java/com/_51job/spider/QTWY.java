package com._51job.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class QTWY implements PageProcessor,Runnable {

    private Site site=Site.me()
            .setDomain("http://www.51job.com/")
            .setRetryTimes(1)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
            .setCharset("utf-8")
            .setTimeOut(5000);
    private static String regJob="http://jobs\\.51job\\.com/.*";
    private static String regEnterp="";
    @Override
    public void run() {

    }

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

    }
}
