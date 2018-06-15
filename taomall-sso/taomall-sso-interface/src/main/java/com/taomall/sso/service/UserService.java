package com.taomall.sso.service;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.pojo.User;

public interface UserService {
    TaomallResult checkData(String data,int type);

    TaomallResult register(User user);

    TaomallResult login(String userName, String password);

    TaomallResult getUserByToken(String token);

    TaomallResult logout(String token);

}
