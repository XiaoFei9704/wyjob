package com._51job.spider;

import com._51job.domain.Dictionary;
import com._51job.domain.Enterprise;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DataProcessor {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    static {
        configuration=new Configuration().configure();
        sessionFactory=configuration.buildSessionFactory();
    }

    public static void main(String[] args) {
        Session session=sessionFactory.openSession();
        Query<Enterprise> query=session.createQuery("from Enterprise where domicile=0",Enterprise.class);
        List<Enterprise> enterprises=query.list();
        Query<Dictionary> query1=session.createQuery("from Dictionary where type=1",Dictionary.class);
        List<Dictionary> dictionaries=query1.list();
        Session session1=sessionFactory.openSession();
        Transaction transaction=session1.beginTransaction();
        for (Enterprise enterprise: enterprises){
            for(Dictionary dictionary:dictionaries){
                String name=dictionary.getDictionaryName().substring(0,2);
                if(enterprise.getName().contains(name)||enterprise.getDescription().contains(name)){
                    System.out.println(name+": "+dictionary.getDictionaryId());
                    enterprise.setDomicile(dictionary.getDictionaryId());
                    session1.saveOrUpdate(enterprise);
                    break;
                }
            }
        }
        transaction.commit();
        session1.close();
    }
}
