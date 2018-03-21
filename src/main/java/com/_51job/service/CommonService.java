package com._51job.service;

import com._51job.dao.CommonDao;
import com._51job.domain.*;
import com._51job.domain.Dictionary;
import com._51job.tool.DataUtil;
import com._51job.tool.KeywordFilter;
import com._51job.web.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
public class CommonService {
    private final CommonDao commonDao;
    private final Jedis jedis;

    @Autowired
    public CommonService(CommonDao commonDao,Jedis jedis) {
        this.commonDao = commonDao;
        this.jedis=jedis;
    }
    //登陆
    public User signin(String username,String password){
        User user=commonDao.getMatchCount(username, password);
        if(user!=null){
            if(user.getRole()==1)user.setName(commonDao.get(Applicant.class,user.getUserId()).getName());
            else user.setName(commonDao.get(Enterprise.class,user.getUserId()).getName());
        }
        return user;
    }
    //账户查重（true:用户名可用；false:用户名重复）
    public boolean checkAccount(String username){
        return commonDao.checkCount(username);
    }
    //获取用户信息（没有登陆则返回null）
    public Enterprise userInfo(int user_id){
        return commonDao.get(Enterprise.class,user_id);
    }

    //获取岗位详情
    public Recruitment job(int recuitment_id){
        return commonDao.get(Recruitment.class,recuitment_id);
    }

    //获得实际地址、规模、企业类型、技能名称、语种、学历、角色
    public String getActualAttribute1(int dicId){
        StringBuffer str=new StringBuffer();
        Integer id= dicId;
        while(id != null){
            Dictionary dic=commonDao.get(Dictionary.class,dicId);
            str.insert(0,dic.getDictionaryName());
            id= dic.getParent();
        }
        return str.toString();
    }
    //根据city在Redis里搜索到一个enterpriseId集合
    public List<Integer> searchEnterpriseIdByCity(String city){
        int cityID=commonDao.getCityId(city);//模糊查询
        String scityID=String.valueOf(cityID);
        List<Integer>enterIds=new ArrayList<>();
        Jedis jedis=new Jedis("localhost");
        Set<String> rs=jedis.smembers(scityID);
        for(String str:rs){
            enterIds.add(Integer.valueOf(str));
        }
        return enterIds;
    }


    //根据enterpriseId集合在Redis里搜索到一个Recruitment对象列表
//    public List<Recruitment> searchRecruitments(Set<String> EnterpriseId){
//        List<Recruitment> allRecruitments=new ArrayList<>();
//        List<Recruitment> partRecruitments;
//        for(String enterpriseId:EnterpriseId){
//            int enterId=Integer.parseInt(enterpriseId);
//            partRecruitments=DataUtil.recruitmentsOfAnEnterprise(enterId);
//            allRecruitments.addAll(partRecruitments);
//        }
//        return allRecruitments;
//    }


    public List<SearchResults> search(String city, String keyword, int salary, String degree, int seniority, int page, int count) throws InterruptedException {
        int city_id=0;
        int min_degree=0;
        int start=count*(page-1);
        List<Dictionary> cities=DataUtil.allCities();
        List<Dictionary> degrees=DataUtil.allDegrees();
        if(city!=null&&city.length()>0){
            for(Dictionary dictionary: cities){
                if(dictionary.getDictionaryName().contains(city)){
                    city_id=dictionary.getDictionaryId();break;
                }
            }
        }
        if(degree!=null){
            for(Dictionary dictionary:degrees){
                if(dictionary.getDictionaryName().contains(degree)){
                    min_degree=dictionary.getDictionaryId();break;
                }
            }
        }
        List<SearchResults> temp=new ArrayList<>();
        List<SearchResults> all=DataUtil.getResults();
        //第一次过滤：关键词
        System.out.println(new Date());
        if(keyword!=null&&keyword.length()>0){
            keyword=keyword.toUpperCase();
            List<String> words= Arrays.asList(keyword.split(" "));
            List<KeywordFilter> filters=DataUtil.getFilters();
            List<Thread> threads=DataUtil.getThreads();
            for(int i=0;i<words.size();i++){
                String word=words.get(i);
                for(int j = i * 75; j<75*(i+1); j++){
                    KeywordFilter filter=filters.get(j);
                    filter.setKeyword(word);
                    filter.setSources(all.subList((j-(i*75))*1200,(j+1-(i*75))*1200));
                    filter.setResults(temp);
                    filter.setFinished(false);
                    while (threads.get(j).getState().equals(Thread.State.RUNNABLE)){
                        j++;
                        filters.get(j).setKeyword(word);
                        filters.get(j).setSources(all.subList((j-(i*75))*1200,(j+1-(i*75))*1200));
                        filters.get(j).setResults(temp);
                        filters.get(j).setFinished(false);
                    }
                    threads.get(j).start();
                }
                KeywordFilter filter=filters.get(9999-i);
                filter.setKeyword(word);
                filter.setSources(all.subList(90000,90393));
                filter.setResults(temp);
                filter.setFinished(false);
                threads.get(9999-i).start();
            }
            for(int i=0,c=75;i<c;){
                if(filters.get(i).isFinished())i++;
                else Thread.sleep(50);
            }
        }else{
            for(SearchResults results: all){
                if(results==null)continue;
                if(results.getRecruitment().getState()==(byte)2)continue;
                temp.add(results);
            }
        }
        if(city_id==0&&salary==0&&min_degree==0&&seniority==0){
            if(start>=temp.size())return null;
            int end;
            if(start+count>temp.size())end=temp.size();
            else end=start+20;
            setActrual(temp.subList(start,end));
            return temp.subList(start,end);
        }
        //第二次过滤：城市
        List<SearchResults> temp2=new ArrayList<>();
        if(city_id!=0){
            for(SearchResults searchResults: temp){
                if(searchResults==null)continue;
                if(searchResults.getRecruitment().getState()==(byte)2)continue;
                if(searchResults.getEnterprise().getDomicile()==city_id){
                    temp2.add(searchResults);
                }
            }
        }else temp2.addAll(temp);
        if(salary==0&&min_degree==0&&seniority==0){
            if(start>=temp2.size())return null;
            int end;
            if(start+count>temp2.size())end=temp2.size();
            else end=start+20;
            setActrual(temp2.subList(start,end));
            return temp2.subList(start,end);
        }
        temp.clear();
        //第三次过滤：工资
        if(salary>0){
            for(SearchResults searchResults: temp2){
                if(searchResults==null)continue;
                if(searchResults.getRecruitment().getSalary()>=salary)temp.add(searchResults);
            }
        }else temp.addAll(temp2);
        temp2.clear();
        if(min_degree==0&&seniority==0){
            if(start>=temp.size())return null;
            int end;
            if(start+count>temp.size())end=temp.size();
            else end=start+20;
            setActrual(temp.subList(start,end));
            return temp.subList(start,end);
        }
        //第四次过滤：学历
        if(min_degree>0){
            for(SearchResults searchResults : temp){
                if(searchResults==null)continue;
                if(searchResults.getRecruitment().getMinDegree()<=min_degree)temp2.add(searchResults);
            }
        }else temp2.addAll(temp);
        temp.clear();
        if(seniority==0){
            if(start>=temp2.size())return null;
            int end;
            if(start+count>temp2.size())end=temp2.size();
            else end=start+20;
            setActrual(temp2.subList(start,end));
            return temp2.subList(start,end);
        }
        //第五次过滤：工作经验
        if(seniority>0){
            for(SearchResults searchResults: temp2){
                if(searchResults==null)continue;
                if(searchResults.getRecruitment().getMinSeniority()<=seniority)temp.add(searchResults);
            }
        }else{
            temp.addAll(temp2);
        }
        if(start>=temp.size())return null;
        int end;
        if(start+count>temp.size())end=temp.size();
        else end=start+20;
        setActrual(temp.subList(start,end));
        return temp.subList(start,end);

        //1、如果关键词和地址皆为空，则不需要查询公司信息表，遍历一遍招聘信息找出符合要求的记录即可
//        if((city_id==0)&&(keyWord == null || keyWord.length()<=0)) {
//
//            long recSum=allRecruitments.size();
//            for (int i = 0; i < recSum; i++) {
//                Enterprise enterpriseMatched;
//                Recruitment recruitmentMatched;
//                SearchResults searchResult;
//                int recId;
//                int enterId;
//                Recruitment rec=allRecruitments.get(i);
//                if ((rec.getSalary() >= salary) && (rec.getMinDegree() < min_degree) && (rec.getMinSeniority() < seniority)) {
//                    recId = rec.getEnterpriseId();
//                    enterId = rec.getEnterpriseId();
//                    recruitmentMatched = DataUtil.getRecruitment(recId);
//                    enterpriseMatched = DataUtil.getEnterprise(enterId);
//                    searchResult = new SearchResults(recruitmentMatched, enterpriseMatched);
//                    rs.add(searchResult);
//                }
//            }
//            return rs;
//        }
//        //2、如果地址为空，则需要先遍历一遍公司信息表，找出name中含有关键词的公司id列表,
//        // 再根据公司id列表找出筛选后的招聘信息，遍历这些记录后找出符合要求的记录
//        else if(city == null||city.length()<=0){
//            List<Integer> enterIds=searchEnterpriseIdBykeyWord(keyWord);
//            List<Recruitment> restRecruitments=new ArrayList<>();
//            for(int i=0;i<enterIds.size();i++){
//                restRecruitments.addAll(DataUtil.recruitmentsOfAnEnterprise(enterIds.get(i)));
//            }
//            for(int j=0;j<restRecruitments.size();j++){
//                Enterprise enterpriseMatched;
//                Recruitment recruitmentMatched;
//                SearchResults searchResult;
//                int recId;
//                int enterId;
//                Recruitment rec=restRecruitments.get(j);
//                if(rec.getPost().contains(keyWord) || rec.getDescription().contains(keyWord) &&(rec.getSalary() >= salary) && (rec.getMinDegree() < min_degree) && (rec.getMinSeniority() < seniority)){
//                    recId = rec.getEnterpriseId();
//                    enterId = rec.getEnterpriseId();
//                    recruitmentMatched = DataUtil.getRecruitment(recId);
//                    enterpriseMatched = DataUtil.getEnterprise(enterId);
//                    searchResult = new SearchResults(recruitmentMatched, enterpriseMatched);
//                    rs.add(searchResult);
//                }
//            }
//            return rs;
//        }
//        //3、如果关键词为空，则需要先遍历一遍公司信息表，找出domicile中含有工作地点的的公司id列表，
//        // 再根据公司id列表找出筛选后的招聘信息，遍历这些记录后找出符合要求的记录
//        else if(keyWord == null || keyWord.length() <= 0){
//            List<Integer>enterIds=searchEnterpriseIdByCity(city);
//            List<Recruitment>restRecruitments=new ArrayList<>();
//            for(int i=0;i<enterIds.size();i++){
//                restRecruitments.addAll(DataUtil.recruitmentsOfAnEnterprise(enterIds.get(i)));
//            }
//            for(int j=0;j<restRecruitments.size();j++){
//                Enterprise enterpriseMatched;
//                Recruitment recruitmentMatched;
//                SearchResults searchResult;
//                int recId;
//                int enterId;
//                Recruitment rec=restRecruitments.get(j);
//                if((rec.getSalary() >= salary) && (rec.getMinDegree() < min_degree) && (rec.getMinSeniority() < seniority)){
//                    recId = rec.getEnterpriseId();
//                    enterId = rec.getEnterpriseId();
//                    recruitmentMatched = DataUtil.getRecruitment(recId);
//                    enterpriseMatched = DataUtil.getEnterprise(enterId);
//                    searchResult = new SearchResults(recruitmentMatched, enterpriseMatched);
//                    rs.add(searchResult);
//                }
//            }
//            return rs;
//        }
//        //如果地址和关键词都不为空，则需要先遍历一遍公司信息表，找出name中含有关键词并且domicile中含有工作地点的的公司id列表，
//        // 再根据公司id列表找出筛选后的招聘信息，遍历这些记录后找出符合要求的记录
//        else {
//            List<Integer> enterIds = searchEnterpriseIdBykeyWordAndCity(city, keyWord);
//            List<Recruitment> restRecruitments = new ArrayList<>();
//            for (int i = 0; i < enterIds.size(); i++) {
//                restRecruitments.addAll(DataUtil.recruitmentsOfAnEnterprise(enterIds.get(i)));
//            }
//            for (int j = 0; j < restRecruitments.size(); j++) {
//                Enterprise enterpriseMatched;
//                Recruitment recruitmentMatched;
//                SearchResults searchResult;
//                int recId;
//                int enterId;
//                Recruitment rec = restRecruitments.get(j);
//                if ((rec.getSalary() >= salary) && (rec.getMinDegree() < min_degree) && (rec.getMinSeniority() < seniority)) {
//                    recId = rec.getEnterpriseId();
//                    enterId = rec.getEnterpriseId();
//                    recruitmentMatched = DataUtil.getRecruitment(recId);
//                    enterpriseMatched = DataUtil.getEnterprise(enterId);
//                    searchResult = new SearchResults(recruitmentMatched, enterpriseMatched);
//                    rs.add(searchResult);
//                }
//            }
//            return rs;
//        }
    }

    private void setActrual(List<SearchResults> results){
        for(SearchResults searchResults: results){
            if(searchResults==null)continue;
            searchResults.getEnterprise().setActualDomicile(DataUtil.getDictionary(searchResults.getEnterprise().getDomicile()).getDictionaryName());
            searchResults.getRecruitment().setActualMinDegree(DataUtil.getDictionary(searchResults.getRecruitment().getMinDegree()).getDictionaryName());
            searchResults.getEnterprise().setActualIndustry(DataUtil.getDictionary(searchResults.getEnterprise().getIndustry()).getDictionaryName());
            searchResults.getEnterprise().setActualScale(DataUtil.getDictionary(searchResults.getEnterprise().getScale()).getDictionaryName());
            searchResults.getEnterprise().setActualType(DataUtil.getDictionary(searchResults.getEnterprise().getType()).getDictionaryName());
        }
    }

}


