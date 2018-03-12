package com._51job.tool;

import com._51job.dao.MyDao;
import com._51job.domain.Dictionary;

import java.util.List;

public class DataUtil {
    private static BeanGetter beanGetter;
    private static MyDao myDao;
    {
        beanGetter=BeanGetter.getBeanGetter();
        myDao=beanGetter.getBean("myDao",MyDao.class);
    }
    public static List<Dictionary> getDic(){
        return myDao.allObjects(Dictionary.class);
    }

}
