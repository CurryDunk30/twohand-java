package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.UserInformation;

import java.util.List;

/**
 * Created by wsk1103 on 2017/4/26.
 */
public interface UserInformationService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInformation record);

    int insertSelective(UserInformation record);

    UserInformation selectByPrimaryKey(Integer id);

    List<UserInformation> selectAll();

    int updateByPrimaryKeySelective(UserInformation record);

    int updateByPrimaryKey(UserInformation record);

    int selectIdByPhone(String phone);

    int selectIdByUsername(String username);

    UserInformation selectByUsername(String username);

    List<UserInformation> getAllForeach(List<Integer> list);


}
