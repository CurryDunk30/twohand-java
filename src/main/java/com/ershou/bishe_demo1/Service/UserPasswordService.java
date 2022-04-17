package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.UserPassword;

/**
 * Created by wsk1103 on 2017/4/27.
 */
public interface UserPasswordService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassword record);

    int insertSelective(UserPassword record);

    UserPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPassword record);

    int updateByPrimaryKey(UserPassword record);

    UserPassword selectByUid(Integer uid);
}
