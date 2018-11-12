package com.allenanker.flashnews.dao;

import com.allenanker.flashnews.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{name}, #{password}, #{salt}, #{headUrl})"})
    int addUser(User user);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE id=#{id}"})
    User selectById(int id);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE name=#{name}"})
    User selectByName(String name);

    @Update({"UPDATE ", TABLE_NAME, " SET password=#{password} WHERE id=#{id}"})
    void updatePassword(User user);

    @Delete({"DELETE FROM ", TABLE_NAME, " WHERE id=#{id}"})
    void deleteById(int id);
}
