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
    private static List<Recruitment> recruitments;
    private static Jedis jedis;
    static {
        jedis=new Jedis("localhost");
        configuration=new Configuration().configure();
        sessionFactory=configuration.buildSessionFactory();
        Session session=getSession();
        Query<Recruitment> query=session.createQuery("from Recruitment ",Recruitment.class);
        recruitments=query.list();
        for(Recruitment recruitment: recruitments){
            jedis.set(("rec" + recruitment.getRecruitmentId()).getBytes(),SerializeUtil.serialize(recruitment));
        }
    }
    private static Session getSession(){
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {
        System.out.println(jedis.get("rec10000"));
    }


}
