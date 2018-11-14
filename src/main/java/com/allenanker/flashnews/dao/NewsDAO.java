package com.allenanker.flashnews.dao;

import com.allenanker.flashnews.model.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsDAO {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{title}, #{link}, #{image}, #{likeCount}, " +
            "#{commentCount}, #{createdDate}, #{userId})"})
    int addNews(News news);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE user_id=#{userId} ORDER BY id DESC LIMIT " +
            "#{offset}," +
            "#{limit}"})
    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " ORDER BY id DESC LIMIT #{offset},#{limit}"})
    List<News> selectLastestNews(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE id=#{id}"})
    News getById(int newsId);

    @Update({"UPDATE ", TABLE_NAME, " SET comment_count = #{commentCount} WHERE id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
