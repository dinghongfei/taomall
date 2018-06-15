package com.taomall.sso.service.impl;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.common.utils.JsonUtils;
import com.taomall.dao.UserMapper;
import com.taomall.jedis.JedisClient;
import com.taomall.pojo.User;
import com.taomall.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_SESSION}")
    private String USER_SESSION;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;


    @Override
    public TaomallResult checkData(String data, int type) {

        int result;
        if (type == 1) {
            result = userMapper.selectByUserName(data);
        } else if(type == 2){
            result = userMapper.selectByPhone(data);
        } else if (type == 3) {
            result = userMapper.selectByEmail(data);
        } else {
            return TaomallResult.build(400,"参数包含非法数据");
        }

        if (result > 0) {
            return TaomallResult.ok(false);
        }

        return TaomallResult.ok(true);
    }

    @Override
    public TaomallResult register(User user) {
        //检查数据有效性
        if (StringUtils.isBlank(user.getUsername())) {
            return TaomallResult.build(400, "用户名不能为空");
        }
        TaomallResult taomallResult = checkData(user.getUsername(), 1);
        if (!(boolean) taomallResult.getData()){
            return TaomallResult.build(400, "用户名重复");
        }
        if (StringUtils.isBlank(user.getPassword())){
            return TaomallResult.build(400, "密码不能为空");
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            taomallResult = checkData(user.getPhone(), 2);
            if (!(boolean) taomallResult.getData()){
                return TaomallResult.build(400, "手机号重复");
            }
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            taomallResult = checkData(user.getEmail(), 3);
            if (!(boolean) taomallResult.getData()){
                return TaomallResult.build(400, "Email重复");
            }
        }
        //补全属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码md5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);

        userMapper.insert(user);

        return TaomallResult.ok();
    }

    @Override
    public TaomallResult login(String userName, String password) {
        //校验用户名和密码是否正确，密码需md5
        int count = userMapper.selectByUserName(userName);
        if (count < 1) {
            return TaomallResult.build(400, "用户名或密码不正确");
        }
        User user = userMapper.selectLogin(userName, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (user == null) {
            return TaomallResult.build(400, "用户名或密码不正确");
        }
        //生成token，使用uuid
        String token = UUID.randomUUID().toString();
        //把用户信息存到redis，可以是token，value是用户信息
        user.setPassword(null);
        jedisClient.set( USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        //设置key的过期时间
        jedisClient.expire(USER_SESSION+":"+token,SESSION_EXPIRE);
        //返回登录成功，其中要把token返回
        return TaomallResult.ok(token);
    }

    @Override
    public TaomallResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if (StringUtils.isBlank(json)) {
            return TaomallResult.build(400, "用户登录已过期");
        }
        jedisClient.expire(USER_SESSION+":"+token,SESSION_EXPIRE);
        //json转换成User对象
        User user = JsonUtils.jsonToPojo(json, User.class);
        return TaomallResult.ok(user);
    }

    @Override
    public TaomallResult logout(String token) {

        jedisClient.expire(USER_SESSION + ":" + token, 0);

        return null;
    }
}
