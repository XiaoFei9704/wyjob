package com._51job.tool;

import com._51job.domain.Dictionary;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataUtil implements Runnable{
    private static SessionFactory sessionFactory;
    private static Jedis jedis;

    static {
        jedis = new Jedis("localhost");
        Configuration configuration = new Configuration().configure();
        sessionFactory= configuration.buildSessionFactory();
        new Thread(new DataUtil()).start();
    }


    private static Session getSession(){
        return sessionFactory.openSession();
    }

    public DataUtil() {
    }

    public static void main(String[] args) {
    }

    public static List<Recruitment> allRecruitments(){
        Set<byte[]> keys=jedis.keys("rec*".getBytes());
        List<Recruitment> recruitments=new ArrayList<>();
        for(byte[] key: keys){
            recruitments.add((Recruitment) SerializeUtil.unserialize(jedis.get(key)));
        }
        return recruitments;
    }

    public static List<Enterprise> allEnterprises(){
        Set<byte[]> keys=jedis.keys("ent*".getBytes());
        List<Enterprise> enterprises=new ArrayList<>();
        for(byte[] key: keys){
            enterprises.add((Enterprise) SerializeUtil.unserialize(jedis.get(key)));
        }
        return enterprises;
    }

    public static List<Dictionary> allDictionaries(){
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            dictionaries.add((Dictionary) SerializeUtil.unserialize(jedis.get(key)));
        }
        return dictionaries;
    }

    public static List<Dictionary> allCities(){
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==1)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allSkills(){
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==5)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allScales(){
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==7)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allDegrees(){
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==9)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Recruitment> recruitmentsOfAnEnterprise(int enterpriseId){
        List<Recruitment> recruitments=new ArrayList<>();
        Set<byte[]> keys=jedis.keys("rec*".getBytes());
        for(byte[] key: keys){
            Recruitment recruitment=(Recruitment)SerializeUtil.unserialize(jedis.get(key));
            if(recruitment.getEnterpriseId().equals(enterpriseId))recruitments.add(recruitment);
        }
        return recruitments;
    }

    private static void query(int min, int count,int t){
        Session session=getSession();
        if(t==1){
            Query<Recruitment> query=session.createQuery("from Recruitment",Recruitment.class);
            query.setFirstResult(min);
            query.setMaxResults(count);
            for(Recruitment recruitment: query.list()){
                jedis.set(("rec"+ recruitment.getRecruitmentId()).getBytes(), SerializeUtil.serialize(recruitment));
            }
        }else {
            Query<Enterprise> query=session.createQuery("from Enterprise ",Enterprise.class);
            query.setFirstResult(min);
            query.setMaxResults(count);
            for(Enterprise enterprise: query.list()){
                jedis.set(("ent"+enterprise.getEnterpriseId()).getBytes(),SerializeUtil.serialize(enterprise));
            }
        }
        session.close();
    }


    @Override
    public void run() {
        Session session=sessionFactory.openSession();
        Query<Dictionary> query=session.createQuery("from Dictionary ", Dictionary.class);
        for(Dictionary dictionary: query.list()){
            jedis.set(("dic"+dictionary.getDictionaryId()).getBytes(),SerializeUtil.serialize(dictionary));
        }
        long redis_count=jedis.dbSize();
        if(redis_count<50000){
            Query c_query=session.createQuery("select count(*) from Recruitment ");
            int rec_count=((Number)c_query.uniqueResult()).intValue();
            c_query=session.createQuery("select count(*) from Enterprise ");
            int ent_count=((Number)c_query.uniqueResult()).intValue();
            for(int i=0;i<rec_count/2000+1;i++){
                query(2000*i,2000,1);
            }
            for(int i=0;i<ent_count/2000+1;i++){
                query(2000*i,2000,2);
            }
        }
    }
}
