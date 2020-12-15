package com.chovy.blog;

import com.chovy.blog.dao.DiscussPostMapper;
import com.chovy.blog.dao.UserMapper;
import com.chovy.blog.entity.DiscussPost;
import com.chovy.blog.entity.Page;
import com.chovy.blog.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlogApplication.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Test
    public void testSelectById(){
        User user=userMapper.selectById(1);
        System.out.println(user);

        user=userMapper.selectByEmail("3066405120@qq.com");
        System.out.println(user);

        user=userMapper.selectByName("chovy");
        System.out.println(user);
    }
    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test3");
        user.setPassword("1721831");
        user.setSalt("abc");
        user.setEmail("482890@qq.com");
        user.setHeaderUrl("dad.jpg");
        user.setCreateTime(new Date());

        int rows=userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());

    }
    @Test
    public void testUpdateUser(){
        int rows = userMapper.updateStatus(1, 2);
        System.out.println(rows);

        rows = userMapper.updateHeader(1, "adcc.jpg");
        System.out.println(rows);

        rows=userMapper.updatePassword(1,"yu781mm");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0, 10);
        for(DiscussPost discussPost:list){
            System.out.println(discussPost);
        }
        int rows=discussPostMapper.selectDiscussPostRows(0);
        System.out.println(rows);
    }

}
