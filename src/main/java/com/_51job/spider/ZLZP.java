package com._51job.spider;

import com._51job.domain.Recruitment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZLZP implements PageProcessor,Runnable {
    private static CopyOnWriteArrayList<Recruitment> jobs;
    private static Session session;
    private static Transaction transaction;
    private static ZLZP zlzp;

    private Site site=Site.me()
            .setDomain("http://www.zhaopin.com")
            .setRetryTimes(1)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
            .setCharset("utf-8")
            .setTimeOut(5000);

    private ZLZP() {
        jobs=new CopyOnWriteArrayList<>();
        Configuration configuration=new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory=configuration.buildSessionFactory();
        session=sessionFactory.openSession();
        transaction=session.beginTransaction();
    }

    public void process(Page page) {
        Html html=page.getHtml();
        List<String> links=html.links().regex("^http://jobs.zhaopin.com.*htm$").all();
        page.addTargetRequests(links);
        String name=html.xpath("/html/body/div[5]/div[1]/div[1]/h1/text()").get();
        System.out.println(name);
        List<String> fulis=html.xpath("/html/body/div[5]/div[1]/div[1]/div[1]/span/text()").all();
        List<String> desc=html.xpath("/html/body/div[6]/div[1]/div[1]/div/div[1]/p/text()").all();
        String company=html.xpath("/html/body/div[5]/div[1]/div[1]/h2/a/text()").get();
        String salary=html.xpath("/html/body/div[6]/div[1]/ul/li[1]/strong/text()").get();
        String addr=html.xpath("/html/body/div[6]/div[1]/ul/li[2]/strong/a/text()").get();
        String pubtime=html.xpath("/html/body/div[6]/div[1]/ul/li[3]/strong/span/text()").get();
        String feature=html.xpath("/html/body/div[6]/div[1]/ul/li[4]/strong/text()").get();
        String exper=html.xpath("/html/body/div[6]/div[1]/ul/li[5]/strong/text()").get();
        String edu=html.xpath("/html/body/div[6]/div[1]/ul/li[6]/strong/text()").get();
        String t_total=html.xpath("/html/body/div[6]/div[1]/ul/li[7]/strong/text()").get();
        String function=html.xpath("/html/body/div[6]/div[1]/ul/li[8]/strong/a/text()").get();
        Pattern pattern=Pattern.compile("[^(0-9)*]");
        Matcher matcher=pattern.matcher(t_total);
        t_total=matcher.replaceAll("").trim();
        int total =Integer.parseInt(t_total);
        Recruitment recruitment=new Recruitment();
        recruitment.setPost(name);
        recruitment.setDescription(desc.toString());
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(salary);
        if(m.find()){
            int a=salary.indexOf("-");
            int b=salary.indexOf("/");
            int low=Integer.parseInt(salary.substring(0,a));
            int high=Integer.parseInt(salary.substring(a+1,b-1));
            int s_int=(low+high)/2;
            recruitment.setSalary(s_int);
        }else {recruitment.setSalary(0);}
        Matcher matcher1=p.matcher(exper);
        if(matcher1.find()){
            int a=salary.indexOf("-");
            int low=Integer.parseInt(exper.substring(0,a));
            recruitment.setMinSeniority(low);
        }else recruitment.setMinSeniority(0);
        recruitment.setState((byte)1);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(pubtime.length()>10&&pubtime.startsWith("2")){
            try {
                Date date=format.parse(pubtime);
                recruitment.setTime(new Timestamp(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            recruitment.setTime(new Timestamp(new Date().getTime()));
        }
        switch (feature){
            case "全职":
                recruitment.setType((byte)1);break;
            case "兼职":
                recruitment.setType((byte)2);break;
            case "实习":
                recruitment.setType((byte)3);break;
        }
        switch (edu){
            case "高中":
                recruitment.setType((byte)2);break;
            case "大专":
                recruitment.setType((byte)3);break;
            case "本科":
                recruitment.setType((byte)4);break;
            case "硕士":
                recruitment.setType((byte)5);break;
            case "博士":
                recruitment.setType((byte)6);break;
        }
        jobs.add(recruitment);
    }

    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        zlzp=new ZLZP();
        new Thread(zlzp).start();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
                for(Recruitment job: jobs){
                    session.save(job);
                }
                jobs.clear();
                transaction.commit();
                transaction=session.beginTransaction();
            }
        },3000,3000);
    }

    @Override
    public void run() {
        String url="http://jobs.zhaopin.com/294467035250112.htm";
        Spider.create(zlzp)
                .addUrl(url)
                .thread(20)
                .run();
    }
}
