package com._51job.dao;

import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.tool.SerializeUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MyDao {

    private static Configuration configuration;
    private static SessionFactory sessionFactory;

	@Autowired
	public MyDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public static Session getSession(){
		return sessionFactory.openSession();
	}

	public void afterTransaction(Session session,Transaction transaction){
		transaction.commit();
		session.close();
	}

	public int save(Object object){
		Session session=getSession();
		Transaction transaction=session.beginTransaction();
		int id=(Integer)session.save(object);
		afterTransaction(session,transaction);
		return id;
	}

    /**
     * 批存储
     */
	public List<Integer> batchSave(List<Object> list){
	    List<Integer> ids=new ArrayList<>();
	    Session session=getSession();
	    Transaction transaction=session.beginTransaction();
	    for (Object object: list){
	        ids.add((Integer) session.save(object));
        }
        afterTransaction(session,transaction);
        return ids;
    }

	public <T> T get(Class<T> tClass, int id){
		return getSession().get(tClass,id);
	}

	public <T> List<T> getPart(Class<T> tClass, int start, int end){
		Query<T> query=getSession().createQuery("from "+tClass.getSimpleName(),tClass);
		query.setFirstResult(start);
		query.setMaxResults(end-start);
		return query.list();
	}

	public void delete(Object object){
		Session session=getSession();
		Transaction transaction=session.beginTransaction();
		session.delete(object);
        afterTransaction(session,transaction);
	}

	public void update(Object object){
		Session session=getSession();
		Transaction transaction=session.beginTransaction();
		session.saveOrUpdate(object);
        afterTransaction(session,transaction);
	}

	public <T> List<T> allObjects(Class<T> tClass){
		Query<T> query=getSession().createQuery("from "+tClass.getName(),tClass);
		return query.list();
	}

	public <T> List<T> objectsWithCondition(Class<T> tClass, String condition){
		Query<T> query=getSession().createQuery("from "+tClass.getSimpleName()+" "+condition, tClass);
		return query.list();
	}

    /**
     * 一对多查询
     * @param id_one 单个的ID
     * @param oneClass 单个的class
     * @param manyClass 多个的class
     */
	public <T> List<T> oneToMany(int id_one, Class oneClass, Class<T> manyClass){
	    Query<T> query=getSession().createQuery("from "+manyClass.getSimpleName()+" where "+oneClass.getSimpleName()+" _id = "+id_one,manyClass);
	    return query.list();
    }


}
