package com._51job.service;

import com._51job.dao.CommonDao;
import com._51job.domain.Dictionary;
import com._51job.domain.Enterprise;
import com._51job.domain.Recruitment;
import com._51job.domain.User;
import com._51job.tool.DataUtil;
import com._51job.tool.SerializeUtil;
import com._51job.web.SearchResults;
import com.sun.org.apache.regexp.internal.RE;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.format.SignStyle;
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
        return user;
    }
    //账户查重（true:用户名可用；false:用户名重复）
    public boolean checkAccount(String username){
        return commonDao.checkCount(username);
    }
    //获取用户信息（没有登陆则返回null）
    public Enterprise userInfo(int user_id){
        Enterprise userInfo=commonDao.get(Enterprise.class,user_id);
        userInfo.setActualIndustry(commonDao.getActualAttribute(userInfo.getIndustry(),commonDao.searchType(userInfo.getIndustry())));
        userInfo.setActualDomicile(commonDao.getActualAttribute(userInfo.getDomicile(),commonDao.searchType(userInfo.getDomicile())));
        userInfo.setActualFoundingTime(commonDao.getActualTime(userInfo.getFoundingTime()));
        userInfo.setActualType(commonDao.getActualAttribute(userInfo.getType(),commonDao.searchType(userInfo.getType())));
        return userInfo;
    }

    //获取岗位详情
    public Recruitment job(int recuitment_id){
        Recruitment job=commonDao.get(Recruitment.class,recuitment_id);
        job.setActualMinDegree(commonDao.getActualAttribute(job.getMinDegree(),commonDao.searchType(job.getMinDegree())));
        String state=(job.getState()==1)?"开放":"关闭";
        job.setActualState(state);
        job.setActualFunction(commonDao.getActualAttribute(job.getFunction(),commonDao.searchType(job.getFunction())));
        job.setActualWorkType(commonDao.getActualAttribute(job.getWorkType(),commonDao.searchType(job.getWorkType())));
        job.setActualTime(commonDao.getActualTime(job.getTime()));
        return job;
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
    //根据keyWord在Redis里搜索到一个enterpriseId列表
    private List<Integer> searchEnterpriseIdBykeyWord(String keyWord){
        List<Enterprise> allEnterprises=DataUtil.allEnterprises();
        List<Integer> enterIds=new ArrayList<>();
        for (Enterprise enterprise : allEnterprises) {
            String name = enterprise.getName();
            if (name.contains(keyWord)) {
                enterIds.add(enterprise.getEnterpriseId());
            }
        }
        return enterIds;
    }
    //根据keyWord和city在Redis里搜索到一个enterpriseId列表
    private List<Integer> searchEnterpriseIdBykeyWordAndCity(String city, String keyWord){
        List<Enterprise> allEnterprises=DataUtil.allEnterprises();
        List<Integer>  enterIds=new ArrayList<>();
        for (Enterprise enter : allEnterprises) {
            String name = enter.getName();
            int cityID = enter.getDomicile();
            int pcityID = commonDao.getCityId(city);
            if (name.contains(keyWord) && cityID == pcityID) {
                enterIds.add(enter.getEnterpriseId());
            }
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


    private List<SearchResults> searchPart(String city, String keyWord, int salary, String degree, int seniority){
        List<Recruitment> allRecruitments=DataUtil.allRecruitments();
        int city_id=0;
        int min_degree=0;
        List<Dictionary> cities=DataUtil.allCities();
        List<Dictionary> degrees=DataUtil.allDegrees();
        if(city!=null){
            for(Dictionary dictionary: cities){
                if(dictionary.getDictionaryName().contains(city))city_id=dictionary.getDictionaryId();
            }
        }
        if(degree!=null){
            for(Dictionary dictionary:degrees){
                if(dictionary.getDictionaryName().contains(degree))min_degree=dictionary.getDictionaryId();
            }
        }
        List<SearchResults> temp=new ArrayList<>();
        //第一次过滤：根据城市
        if(city_id!=0){
            List<Enterprise> enterprises=DataUtil.getEnterprisesByDomicile(city_id);
            Map<Integer,Enterprise> map=new HashMap<>();
            for(Enterprise enterprise: enterprises)map.put(enterprise.getEnterpriseId(),enterprise);
            for(Recruitment recruitment: allRecruitments){
                if(map.keySet().contains(recruitment.getEnterpriseId())){
                    temp.add(new SearchResults(recruitment,map.get(recruitment.getEnterpriseId())));
                }
            }
        }else{
            for(Recruitment recruitment: allRecruitments)temp.add(new SearchResults(recruitment,DataUtil.getEnterprise(recruitment.getEnterpriseId())));
        }
        //第二次过滤：根据关键词，关键词用空格分开，每个词语可能是公司名或者职位名之部分
        List<SearchResults> temp2=new ArrayList<>();
        if(keyWord!=null){
            List<String> words=Arrays.asList(keyWord.split(" "));
            for(String word : words){
                for(SearchResults searchResults: temp) {
                    if (searchResults.getRecruitment().getPost().contains(word) || searchResults.getEnterprise().getName().contains(word)) temp2.add(searchResults);
                }
            }
        }else temp2.addAll(temp);
        temp.clear();
        //第三次过滤：工资
        if(salary>0){
            for(SearchResults searchResults: temp2){
                if(searchResults.getRecruitment().getSalary()>=salary)temp.add(searchResults);
            }
        }else temp.addAll(temp2);
        temp2.clear();
        //第四次过滤：学历
        if(min_degree>0){
            for(SearchResults searchResults : temp){
                if(searchResults.getRecruitment().getMinDegree()<=min_degree)temp2.add(searchResults);
            }
        }else temp2.addAll(temp);
        temp.clear();
        //第五次过滤：工作经验
        if(seniority>=0){
            for(SearchResults searchResults: temp2){
                if(searchResults.getRecruitment().getMinSeniority()<=seniority)temp.add(searchResults);
            }
        }
        return temp;



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
    public List<SearchResults> search(String city,String keyWord, int salary, String degree, int seniority,int page,int count){
        List<SearchResults> allResults=searchPart(city,keyWord,salary,degree,seniority);
        List<SearchResults> result=new ArrayList<>();
        int sum=allResults.size();
        int start=(page-1)*count;
        int end=start+count;
        if(start>sum) return null;
        for(int i=start;i<end;i++){
            if(i>=allResults.size())break;
            result.add(allResults.get(i));
        }
        return result;
    }

    ///测试查询类型方法是否可用
    public int type(int dicID){
        return commonDao.searchType(dicID);
    }
}


