package com.allenanker.flashnews.dao;

import com.allenanker.flashnews.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{fromId}, #{toId}, #{content}, " +
            "#{createdDate}, #{hasRead}, #{conversationId})"})
    int addMessage(Message message);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE conversation_id=#{convId} ORDER BY id DESC " +
            "LIMIT #{offset},#{limit}"})
    List<Message> selectMessagesByConvId(@Param("convId") String convId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Select({"SELECT ", INSERT_FIELDS, ", COUNT(*) AS id FROM (SELECT * FROM ", TABLE_NAME, " WHERE " +
            "from_id=#{userId} OR to_id=#{userId} ORDER BY id DESC) tt GROUP BY conversation_id ORDER BY id DESC " +
            "LIMIT #{offset},#{limit}"})
    List<Message> selectConversationList(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);
}
