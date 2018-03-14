package com._51job.service;

import com._51job.dao.MyDao;
import com._51job.domain.Dictionary;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.tool.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;


@Service
public abstract class MyService {
	private final MyDao myDao;

	@Autowired
	public MyService(MyDao myDao, Jedis jedis) {
		this.myDao = myDao;
        this.jedis = jedis;
    }
	private final Jedis jedis;

	public List<Recruitment> search(String city, String keyword,int lowSalary, int highSalary, int experience, int xueli){
	    List<Dictionary> cities=myDao.objectsWithCondition(Dictionary.class,"where type=1");
	    int city_id=0;
	    for(Dictionary dictionary:cities){
	        if(dictionary.getDictionaryName().contains(city))city_id=dictionary.getDictionaryId();
        }
        String city_condition=" enterprise_id in (select enterprise_id in (select * from enterprise where domicile="+city_id+")c)";
	    String salary_condition=" salary between "+lowSalary+" and "+highSalary;
	    String exper_condition=" min_seniority>"+experience;
	    String xueli_condition=" min_degree>"+xueli;
	    List<Recruitment> recruitments=myDao.objectsWithCondition(Recruitment.class, "where"+city_condition+" and"+salary_condition+" and"+exper_condition+" and"+xueli_condition);
	    return recruitments;
	}

    public static void main(String[] args) {
        Jedis jedis=new Jedis("localhost");
        System.out.println(SerializeUtil.unserialize(jedis.get("rec9".getBytes())));
    }


    public List<Recruitment> search(String key, int count, int low){
	    List<Recruitment> rs=new ArrayList<>();
	    for(int i=0;i<10000;i++){
	        Recruitment recruitment=(Recruitment) SerializeUtil.unserialize(jedis.get(("rec"+i).getBytes()));
	        if((recruitment.getPost().contains(key)||recruitment.getDescription().contains(key))&&(recruitment.getSalary()>=low))rs.add(recruitment);
	        if(rs.size()>=count)break;
        }
        return rs;
    }
}
