package com.chovy.blog.dao;

import com.chovy.blog.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset,int limit);

    int selectCountByEntity(int entityType,int entityId);
}
