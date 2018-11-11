package com.allenanker.flashnews.dao;

import com.allenanker.flashnews.model.News;
import com.allenanker.flashnews.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsDAO {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{title}, #{link}, #{image}, #{likeCount}, " +
            "#{commentCount}, #{createdDate}, #{userId})"})
    int addNews(News news);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE id=#{userId} ORDER BY id DESC LIMIT #{offset}," +
            "#{limit}"})
    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);
}
