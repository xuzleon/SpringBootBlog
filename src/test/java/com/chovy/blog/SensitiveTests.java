package com.chovy.blog;

import com.chovy.blog.util.SensitiveFilter;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlogApplication.class)
public class SensitiveTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public void testSensitiveFilter(){
        String text="按摩棒，赌博❤，哈哈！";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
