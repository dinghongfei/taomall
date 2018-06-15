package com.taomall.dao;

import com.taomall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectByUserName(String userName);
    int selectByPhone(String phone);
    int selectByEmail(String email);

    User selectLogin(String userName,String password);
}