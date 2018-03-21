package com._51job.tool;

import com._51job.domain.*;
import com._51job.web.SearchResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class  DataUtil implements Runnable{
    private static SessionFactory sessionFactory;
    private static List<Dictionary> dictionaries1;

    public static List<SearchResults> getResults() {
        return results;
    }

    private static List<SearchResults> results=new ArrayList<>();
    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    private static Jedis s_jedis;

    public static List<KeywordFilter> getFilters() {
        return filters;
    }

    private static List<KeywordFilter> filters=new ArrayList<>();

    public static List<Thread> getThreads() {
        return threads;
    }

    private static List<Thread> threads=new ArrayList<>();

    static {
        Configuration configuration = new Configuration().configure();
        sessionFactory= configuration.buildSessionFactory();
        dictionaries1=new ArrayList<>();
        s_jedis=pool.getResource();
        new Thread(new DataUtil()).start();
        for(int i=0;i<10000;i++){
            filters.add(new KeywordFilter());
            threads.add(new Thread(filters.get(i)));
        }
    }
    public void setThreads(){
        threads.clear();
        for (int i=0;i<10000;i++){
            threads.add(new Thread(filters.get(i)));
        }
        System.out.println("已经重置所有搜索线程。");
    }


    private static Session getSession(){
        return sessionFactory.openSession();
    }

    public DataUtil() {
    }

    //根据一个enterpriseId在redis里搜索到该公司的信息
    public static Enterprise getEnterprise(int enterpriseId){
        byte[] key=String.valueOf("ent"+enterpriseId).getBytes();
        return (Enterprise) SerializeUtil.unserialize(s_jedis.get(key));
    }
    //根据一个recruitmentId在redis里搜索到该公司的信息
    public static Recruitment getRecruitment(int recruitmentId){
        byte[]key=String.valueOf("rec"+recruitmentId).getBytes();
        return (Recruitment) SerializeUtil.unserialize(s_jedis.get(key));
    }

    public static List<Dictionary> allDictionaries(){
        return dictionaries1;
    }

    public static Dictionary getDictionary(int dictionaryId){
        s_jedis.select(0);
        byte[] key=("dic"+dictionaryId).getBytes();
        return (Dictionary)SerializeUtil.unserialize(s_jedis.get(key));
    }

    public static List<Dictionary> allCities(){
        Jedis jedis = pool.getResource();
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
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==5)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }

    public static List<Dictionary> allScales(){
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==7)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }

    public static List<Dictionary> allDegrees(){
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==9)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }

    public static List<Dictionary> allIndystries(){
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==2)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }

    public static List<Dictionary> allLanguages(){
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==6)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }

    public static List<Dictionary> allFuctions(){
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==3)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }



    public static List<Dictionary> allEnterpriseType(){
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> keys=jedis.keys("dic*".getBytes());
        List<Dictionary> dictionaries=new ArrayList<>();
        for(byte[] key: keys){
            Dictionary dictionary = (Dictionary) SerializeUtil.unserialize(jedis.get(key));
            if(dictionary.getType()==4)dictionaries.add(dictionary);
        }
        jedis.close();
        return dictionaries;
    }

    public static List<Enterprise> getEnterprisesByDomicile(int domicile){
        Jedis jedis = pool.getResource();
        jedis.select(0);
        Set<byte[]> enter_ids=jedis.smembers(("d_p"+domicile).getBytes());
        List<Enterprise> enterprises=new ArrayList<>();
        for(byte[] enterid: enter_ids){
            int id=byteArrayToInt(enterid);
            enterprises.add((Enterprise) SerializeUtil.unserialize(jedis.get(("ent"+id).getBytes())));
        }
        jedis.close();
        return enterprises;
    }



    private static void query(int min, int count,int t){
        Jedis jedis = pool.getResource();
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
                jedis.sadd(("d_p"+enterprise.getDomicile()).getBytes(),intToByteArray(enterprise.getEnterpriseId()));
            }
        }
        session.close();
        jedis.close();
    }


    @Override
    public void run() {
        Jedis jedis = pool.getResource();
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
        }
        System.out.println("预加载开始");
        Set<byte[]> keys=jedis.keys("rec*".getBytes());
        for(byte[] key: keys) {
            Recruitment recruitment = (Recruitment) SerializeUtil.unserialize(jedis.get(key));
            Enterprise enterprise=getEnterprise(recruitment.getEnterpriseId());
            if(enterprise != null){
                results.add(new SearchResults(recruitment, enterprise));
                System.out.println(recruitment);
            }
        }
        System.out.println("预加载完成");
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
        Jedis jedis = pool.getResource();
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(2);
        jedis.sadd(String.valueOf(userId),String.valueOf(recruitmentId));
        jedis.close();
    }
    //获取推荐给id的职位
    public static List<Recruitment> recommends(int userId){
        Jedis jedis = pool.getResource();
        List<Recruitment> recruitments=new ArrayList<>();
        if(!jedis.isConnected())jedis=new Jedis("localhost");
        jedis.select(1);
        Set<String> keys=jedis.smembers(String.valueOf(userId));
        for(String key: keys){
            Recruitment recruitment=(Recruitment) SerializeUtil.unserialize(jedis.get(key).getBytes());
            recruitments.add(recruitment);
        }
        jedis.close();
        return recruitments;
    }

    public void closeJob(int id){
        for(SearchResults result: results){
            if(result.getRecruitment().getRecruitmentId()==id){
                result.getRecruitment().setState((byte)2);break;
            }
        }
        System.out.println("已关闭id为"+id+"的招聘信息");
    }
    public void openJob(int id){
        for(SearchResults result : results){
            if(result.getRecruitment().getRecruitmentId()==id){
                result.getRecruitment().setState((byte)1);break;
            }
        }
        System.out.println("已开启id为"+id+"的招聘信息");
    }
}
