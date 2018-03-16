package com._51job.spider;

import com._51job.domain.*;
import com._51job.domain.Dictionary;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
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
    private static CopyOnWriteArrayList<Enterprise> enterprises;
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Session session2;
    private static Transaction transaction;
    private static Transaction transaction2;
    private static ZLZP zlzp;
    private static String regCompany="http://company\\.zhaopin\\.com.*htm$";
    private static String regJob="^http://jobs.zhaopin.com.*htm$";
    private static Set<String> comName;
    private static Set<String> jobDesc;
    private static List<Dictionary> enterType;
    private static List<Dictionary> enterIndus;
    private static List<Dictionary> enterScale;
    private static List<Dictionary> places;
    private static List<Dictionary> skills;
    private static List<Dictionary> xueli;
    private static List<Enterprise> enterPrise_e;
    private static List<Recruitment> recruitments;

    private Site site=Site.me()
            .setDomain("http://www.zhaopin.com")
            .setRetryTimes(1)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
            .setCharset("utf-8")
            .setTimeOut(5000);

    private ZLZP() {
        jobs=new CopyOnWriteArrayList<>();
        enterprises=new CopyOnWriteArrayList<>();
        configuration=new Configuration().configure("hibernate.cfg.xml");
        sessionFactory=configuration.buildSessionFactory();
        session=sessionFactory.openSession();
        session2=sessionFactory.openSession();
        transaction=session.beginTransaction();
        transaction2=session2.beginTransaction();
        comName=new HashSet<>();
        enterType=new ArrayList<>();
        enterIndus=new ArrayList<>();
        enterScale=new ArrayList<>();
        places=new ArrayList<>();
        skills=new ArrayList<>();
        xueli=new ArrayList<>();
        enterPrise_e=new ArrayList<>();
        recruitments=new ArrayList<>();
        jobDesc=new HashSet<>();
    }
    public Session getSession(){return sessionFactory.openSession();}

    public void process(Page page) {
        Html html=page.getHtml();
        List<String> links=html.links().regex(regCompany).all();
        List<String> links2=html.links().regex(regJob).all();
        page.addTargetRequests(links);
        page.addTargetRequests(links2);
        if(page.getUrl().regex(regJob).match()){
            String name=html.xpath("/html/body/div[5]/div[1]/div[1]/h1/text()").get();
            List<String> fulis=html.xpath("/html/body/div[5]/div[1]/div[1]/div[1]/span/text()").all();
            String description=delHTMLTag(html.xpath("/html/body/div[6]/div[1]/div[1]/div/div[1]/p").all().toString());
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
            description=description.replace("[","");
            description=description.replace("]","");
            description=description.replace("：, ",":");
            description=description.replace("。, ","。 ");
            description=description.replace(", ,",",");
            recruitment.setDescription(description);
            for(Dictionary dictionary:skills){
                if(name.contains(dictionary.getDictionaryName())||description.contains(dictionary.getDictionaryName())||name.toUpperCase().contains(dictionary.getDictionaryName().toUpperCase())||description.toUpperCase().contains(dictionary.getDictionaryName().toUpperCase())){
                    recruitment.setFunction(dictionary.getDictionaryId());
                }
            }
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
                int a=exper.indexOf("-");
                if(a>-1){
                    int low=Integer.parseInt(exper.substring(0,a));
                    recruitment.setMinSeniority(low);
                }
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
                    recruitment.setWorkType((byte)1);break;
                case "兼职":
                    recruitment.setWorkType((byte)2);break;
                case "实习":
                    recruitment.setWorkType((byte)3);break;
            }
            for(Dictionary dictionary:xueli){
                if(edu.equals(dictionary.getDictionaryName()))recruitment.setMinDegree(dictionary.getDictionaryId());
            }
            for(Enterprise enterprise: enterPrise_e){
                if(enterprise.getName().equals(company)||company.contains(enterprise.getName())||enterprise.getName().contains(company))recruitment.setEnterpriseId(enterprise.getEnterpriseId());
            }
            if(description.length()>20)jobs.add(recruitment);
        }else if(page.getUrl().regex(regCompany).match()){
            String name=html.xpath("/html/body/div[2]/div[1]/div[1]/h1/text()").get();
            if(!comName.contains(name)){
                comName.add(name);
                String type=html.xpath("/html/body/div[2]/div[1]/div[1]/table/tbody/tr[1]/td[2]/span/text()").get();
                int i_type_id=enterpriseType(type);
                String scale=html.xpath("/html/body/div[2]/div[1]/div[1]/table/tbody/tr[2]/td[2]/span/text()").get();
                int i_scale_id=enterPriseScale(scale);
                String industry=html.xpath("/html/body/div[2]/div[1]/div[1]/table/tbody/tr[4]/td[2]/span/text()").get();
                String[] inds=industry.split(",");
                int inds_id=0;
                for(String s:inds){
                    inds_id=industry(s);
                    if(inds_id>0)break;
                }
                if(inds_id==0)inds_id=967;
                String addr=html.xpath("/html/body/div[2]/div[1]/div[1]/table/tbody/tr[5]/td[2]/span/text()").get();
                String desc=html.xpath("/html/body/div[2]/div[1]/div[2]/div/text()").get();
                desc+=delHTMLTag(html.xpath("/html/body/div[2]/div[1]/div[2]/div").get());
                Enterprise enterprise=new Enterprise();
                enterprise.setName(name);
                enterprise.setType(i_type_id);
                enterprise.setScale(i_scale_id);
                enterprise.setIndustry(inds_id);
                enterprise.setAddress(addr);
                enterprise.setDescription(desc);
                enterprise.setDomicile(181);
                enterprise.setFoundingTime(new Timestamp(new Date().getTime()));
                if(enterprise.getIndustry()!=0&&enterprise.getType()!=0&&enterprise.getAddress()!=null)enterprises.add(enterprise);
            }

        }
    }

    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        zlzp=new ZLZP();
        Query<Enterprise> query=zlzp.getSession().createQuery("from Enterprise",Enterprise.class);
        enterPrise_e=query.list();
        for(Enterprise enterprise:enterPrise_e){
            comName.add(enterprise.getName());
        }
        Query<Dictionary> query1=zlzp.getSession().createQuery("from Dictionary where type=2",Dictionary.class);
        enterIndus=query1.list();
        Query<Dictionary> query2=zlzp.getSession().createQuery("from Dictionary  where type=4", Dictionary.class);
        enterType=query2.list();
        Query<Dictionary> query3=zlzp.getSession().createQuery("from Dictionary  where type=7",Dictionary.class);
        enterScale=query3.list();
        Query<Dictionary> query4=zlzp.getSession().createQuery("from Dictionary where type=1", Dictionary.class);
        places=query4.list();
        Query<Dictionary> query5=zlzp.getSession().createQuery("from Dictionary where type=5", Dictionary.class);
        skills=query5.list();
        Query<Dictionary> query6=zlzp.getSession().createQuery("from Dictionary  where type=9", Dictionary.class);
        xueli=query6.list();
//        Query<Recruitment> query7=zlzp.getSession().createQuery("from Recruitment", Recruitment.class);
//        recruitments=query7.list();
//        for(Recruitment recruitment: recruitments)jobDesc.add(recruitment.getDescription());
        new Thread(zlzp).start();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
                enterPrise_e.addAll(enterprises);
                for(Enterprise enterprise: enterprises){
                    session.save(enterprise);
                }
                enterprises.clear();
                transaction.commit();
                transaction=session.beginTransaction();
            }
        },3000,3000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date()+"存储职位");
                for(Recruitment recruitment:jobs){
                    session2.save(recruitment);
                }
                jobs.clear();
                transaction2.commit();
                transaction2=session2.beginTransaction();
            }
        },3000,3000);
    }

    @Override
    public void run() {
        List<String> urls=new ArrayList<>();
        for(int i=0;i<40;i++){
            for (Dictionary dictionary1 : skills) {
                String skill = dictionary1.getDictionaryName();
                urls.add("http://sou.zhaopin.com/jobs/searchresult.ashx?jl=西安&kw=" + skill + "&isadv=0&sg=0390cfbf86794329aa5a271e7fb4029d&p=" + (i + 1));
            }
        }
        String n="http://sou.zhaopin.com/jobs/searchresult.ashx?jl=深圳&kw=python&isadv=0&sg=0390cfbf86794329aa5a271e7fb4029d&p=";
        Spider.create(zlzp)
                .addUrl(urls.toArray(new String[urls.size()]))
                .thread(20)
                .run();
    }
    private static int industry(String s_industry){
        for(Dictionary dictionary: enterIndus){
            if(dictionary.getDictionaryName().equals(s_industry))return dictionary.getDictionaryId();
        }
        return 0;
    }
    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
        String nbsp="&nbsp;";

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        Pattern nb_=Pattern.compile(nbsp,Pattern.CASE_INSENSITIVE);
        Matcher m_htm=nb_.matcher(htmlStr);
        htmlStr=m_htm.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }
    private int enterpriseType(String s_type){
        for (Dictionary dictionary: enterType){
            if(dictionary.getDictionaryName().equals(s_type))return dictionary.getDictionaryId();
        }
        return 0;
    }

    private int enterPriseScale(String s_scale){
        for (Dictionary dictionary: enterScale){
            if(dictionary.getDictionaryName().equals(s_scale))return dictionary.getDictionaryId();
        }
        return 0;
    }
}
