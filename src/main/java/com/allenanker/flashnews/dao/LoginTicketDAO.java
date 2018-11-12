package com.allenanker.flashnews.dao;

import com.allenanker.flashnews.model.LoginTicket;
import com.allenanker.flashnews.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginTicketDAO {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{userId}, #{ticket}, #{expired}, #{status})"})
    int addTicket(LoginTicket ticket);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE id=#{id}"})
    LoginTicket selectById(int id);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"UPDATE ", TABLE_NAME, " SET status=#{status} WHERE ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

    @Delete({"DELETE FROM ", TABLE_NAME, " WHERE id=#{id}"})
    void deleteById(int id);
}
