package com.chovy.blog;


import com.chovy.blog.config.AlphaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlogApplication.class)
public class BlogApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);
	}
	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat= (SimpleDateFormat) applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}
	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testDI(){
		System.out.println(simpleDateFormat);
	}
}
