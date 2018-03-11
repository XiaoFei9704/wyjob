package com._51job.web;

import com._51job.domain.Recruitment;
import com._51job.service.MyService;
import com._51job.tool.BeanGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import sun.awt.image.PixelConverter;

import java.util.List;

@Controller
@RequestMapping(value = "/api")
public class MyController {
	private final MyService myService;

	@Autowired
	public MyController(MyService myService) {
		this.myService = myService;
	}

	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	@ResponseBody
	public List<Recruitment> allJobs(int start, int end){
		if(end<=start)return null;
		return null;
	}

	@RequestMapping(value = "/test")
	public ModelAndView test(){
        return new ModelAndView("index");
	}

	@RequestMapping(value = "/redisTest")
	@ResponseBody
	public String getString(String key){
		BeanGetter beanGetter= BeanGetter.getBeanGetter();
		Jedis jedis=beanGetter.getBean("jedis", Jedis.class);
		return jedis.get(key);
	}

	@RequestMapping(value = "/setKey")
    @ResponseBody
    public String setString(String key, String value){
        BeanGetter beanGetter= BeanGetter.getBeanGetter();
        Jedis jedis=beanGetter.getBean("jedis", Jedis.class);
        return jedis.set(key,value);
    }
    @RequestMapping(value = "/print")
    @ResponseBody
    public String print(){
	    return "ok";
    }
}
