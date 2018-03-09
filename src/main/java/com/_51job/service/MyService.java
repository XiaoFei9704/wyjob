package com._51job.service;

import com._51job.dao.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MyService {
	private final MyDao myDao;

	@Autowired
	public MyService(MyDao myDao) {
		this.myDao = myDao;
	}

}
