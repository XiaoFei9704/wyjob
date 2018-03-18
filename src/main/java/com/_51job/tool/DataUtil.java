package com._51job.tool;

import com._51job.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class  DataUtil implements Runnable{
    private static SessionFactory sessionFactory;
    private static Jedis jedis;
    private static List<Recruitment> recruitments;
    private static List<Enterprise> enterprises;
    private static List<Dictionary> dictionaries1;

    static {
        jedis = new Jedis("localhost");
        Configuration configuration = new Configuration().configure();
        sessionFactory= configuration.buildSessionFactory();
        recruitments=new ArrayList<>();
        enterprises=new ArrayList<>();
        dictionaries1=new ArrayList<>();
        new Thread(new DataUtil()).start();
    }


    private static Session getSession(){
        return sessionFactory.openSession();
    }

    public DataUtil() {
    }

    public static void main(String[] args) {
        Session session=getSession();
        Transaction transaction=session.beginTransaction();
        Set<byte[]> keys=jedis.keys("ent*".getBytes());
        for(byte[] key: keys){
            enterprises.add((Enterprise)SerializeUtil.unserialize(jedis.get(key)));
        }
        for(Enterprise enterprise: enterprises){
            User user=new User();
            user.setUserId(enterprise.getEnterpriseId());
            user.setRole(2);
            user.setUserName("ent"+enterprise.getEnterpriseId());
            user.setPassword("123456");
            session.save(user);
        }
        transaction.commit();
        session.close();
    }

    public static List<Recruitment> allRecruitments(){
        return recruitments;
    }

    public static List<Enterprise> allEnterprises(){
        return enterprises;
    }
    //根据一个enterpriseId在redis里搜索到该公司的所有招聘信息
    public static List<Recruitment> recruitmentsOfAnEnterprise(int enterpriseId){
        List<Recruitment> recruitments=new ArrayList<>();
        Set<byte[]> keys=jedis.keys("rec*".getBytes());
        for(byte[] key: keys){
            Recruitment recruitment=(Recruitment) SerializeUtil.unserialize(jedis.get(key));
            if(recruitment.getEnterpriseId().equals(enterpriseId))recruitments.add(recruitment);
        }
        return recruitments;
    }
    //根据一个enterpriseId在redis里搜索到该公司的信息
    public static Enterprise getEnterprise(int enterpriseId){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        byte[] key=String.valueOf("ent"+enterpriseId).getBytes();
        return (Enterprise) SerializeUtil.unserialize(jedis.get(key));
    }
    //根据一个recruitmentId在redis里搜索到该公司的信息
    public static Recruitment getRecruitment(int recruitmentId){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        byte[]key=String.valueOf("rec"+recruitmentId).getBytes();
        return (Recruitment) SerializeUtil.unserialize(jedis.get(key));
    }

    public static List<Dictionary> allDictionaries(){
        return dictionaries1;
    }

    public static Dictionary getDictionary(int dictionaryId){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        byte[] key=("dic"+dictionaryId).getBytes();
        return (Dictionary)SerializeUtil.unserialize(jedis.get(key));
    }

    public static List<Dictionary> allCities(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==1)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }

    public static List<Dictionary> allSkills(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==5)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allScales(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==7)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allDegrees(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==9)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allIndystries(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==2)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allLanguages(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==6)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Dictionary> allFuctions(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==3)dictionaries.add(dictionary);
        }
        return dictionaries;
    }



    public static List<Dictionary> allEnterpriseType(){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==4)dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    public static List<Enterprise> getEnterprisesByDomicile(int domicile){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(0);
        Set<byte[]> enter_ids=jedis.smembers(("d_p"+domicile).getBytes());
        List<Enterprise> enterprises=new ArrayList<>();
        for(byte[] enterid: enter_ids){
            int id=byteArrayToInt(enterid);
            enterprises.add((Enterprise) SerializeUtil.unserialize(jedis.get(("ent"+id).getBytes())));
        }
        return enterprises;
    }



    private static void query(int min, int count,int t){
        Session session=getSession();
        if(t==1){
            Query<Recruitment> query=session.createQuery("from Recruitment",Recruitment.class);
            query.setFirstResult(min);
            query.setMaxResults(count);
            for(Recruitment recruitment: query.list()){
                jedis.set(("rec"+ recruitment.getRecruitmentId()).getBytes(), SerializeUtil.serialize(recruitment));
                recruitments.add(recruitment);
            }
        }else {
            Query<Enterprise> query=session.createQuery("from Enterprise ",Enterprise.class);
            query.setFirstResult(min);
            query.setMaxResults(count);
            for(Enterprise enterprise: query.list()){
                jedis.set(("ent"+enterprise.getEnterpriseId()).getBytes(),SerializeUtil.serialize(enterprise));
                jedis.sadd(("d_p"+enterprise.getDomicile()).getBytes(),intToByteArray(enterprise.getEnterpriseId()));
                enterprises.add(enterprise);
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
            dictionaries1.add(dictionary);
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
        }else{
            System.out.println("预加载开始");
            Set<byte[]> keys=jedis.keys("rec*".getBytes());
            for(byte[] key: keys){
                recruitments.add((Recruitment) SerializeUtil.unserialize(jedis.get(key)));
            }
            System.out.println("加载完招聘信息");
            keys=jedis.keys("ent*".getBytes());
            for(byte[] key: keys){
                enterprises.add((Enterprise) SerializeUtil.unserialize(jedis.get(key)));
            }
            System.out.println("预加载完毕！");
        }
        jedis.close();
    }

    private static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
    private static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
    //2号库保存用户浏览记录
    public static void saveLog(int userId, int recruitmentId){
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(2);
        jedis.sadd(intToByteArray(userId),intToByteArray(recruitmentId));
    }
    //获取推荐给id的职位
    public static List<Recruitment> recommends(int userId){
        List<Recruitment> recruitments=new ArrayList<>();
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(1);
        Set<byte[]> keys=jedis.smembers(intToByteArray(userId));
        for(byte[] key: keys){
            Recruitment recruitment=(Recruitment) SerializeUtil.unserialize(jedis.get(key));
            recruitments.add(recruitment);
        }
        return recruitments;
    }
}
