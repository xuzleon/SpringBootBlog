package com.chovy.blog.dao;

import com.chovy.blog.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);
    //@Parm 用于为参数取别名，
    //如果只有一个参数，并且在<if>里使用，则需要取别名。
    int selectDiscussPostRows(@Param("userId") int userId);
    //发帖
    int insertDiscussPost(DiscussPost discussPost);
    //详情
    DiscussPost selectDiscussPostById(int id);
}
