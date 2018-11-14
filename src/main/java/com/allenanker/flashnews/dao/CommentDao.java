package com.allenanker.flashnews.dao;

import com.allenanker.flashnews.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = " user_id, entity_id, entity_type, content, created_date, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{userId}, #{entityId}, #{entityType}, " +
            "#{content}, #{createdDate}, #{status})"})
    int addComment(Comment comment);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE entity_id=#{entityId} AND " +
            "entity_type=#{entityType} ORDER BY id DESC"})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"SELECT COUNT(*) FROM ", TABLE_NAME, " WHERE entity_id=#{entityId} AND " +
            "entity_type=#{entityType} ORDER BY id DESC"})
    int getCommentsCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
}