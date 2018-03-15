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
public class MyService {
	private final MyDao myDao;

	@Autowired
	public MyService(MyDao myDao, Jedis jedis) {
		this.myDao = myDao;
        this.jedis = jedis;
    }
	private final Jedis jedis;



    public static void main(String[] args) {
        Jedis jedis=new Jedis("localhost");
        System.out.println(SerializeUtil.unserialize(jedis.get("rec9".getBytes())));
    }


    public List<Recruitment> search(String keyWord, int min_Salary,int min_degree,int min_seniority,int count){
		//String key=(keyWord==null)?"1=1":;
		//String add=(address==null)?"1=1":address;
	    List<Recruitment> rs=new ArrayList<>();
	    for(int i=0;i<10000;i++){
	        Recruitment recruitment=(Recruitment) SerializeUtil.unserialize(jedis.get(("rec"+i).getBytes()));
	        if(keyWord == null || keyWord.length() <= 0){
				if((recruitment.getSalary()>=min_Salary)&&(recruitment.getMinDegree()<min_degree)&&(recruitment.getMinSeniority()<min_seniority))
					rs.add(recruitment);
			}
	        else {
	        	if((recruitment.getPost().contains(keyWord)||recruitment.getDescription().contains(keyWord))&&(recruitment.getSalary()>=min_Salary)&&(recruitment.getMinDegree()<min_degree)&&(recruitment.getMinSeniority()<min_seniority))
	        		rs.add(recruitment);
	        }
	        if(rs.size()>=count)break;
        }
        return rs;
    }

}
