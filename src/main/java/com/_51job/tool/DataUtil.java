package com._51job.tool;

import com._51job.domain.Recruitment;
import com._51job.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static int rec_total;
//    private static Jedis jedis;
//    static {
//        jedis=new Jedis("localhost");
//        configuration=new Configuration().configure();
//        sessionFactory=configuration.buildSessionFactory();
//        Session session=getSession();
//
//        Query<Recruitment> query=session.createQuery("from Recruitment",Recruitment.class);
//        query.setFirstResult(0);
//        query.setMaxResults(10000);
//        List<Recruitment> recruitments=query.list();
//        rec_total=recruitments.size();
//        for(int i=0;i<rec_total;i++){
//            Recruitment recruitment=recruitments.get(i);
//            jedis.set(("rec" + i).getBytes(),SerializeUtil.serialize(recruitment));
//        }
//    }
    private static Session getSession(){
        return sessionFactory.openSession();
    }

//
//    public static List<Recruitment> allRecruitments(){
//        for(int i=0;i<rec_total;i++){
//
//        }
//    }


}
