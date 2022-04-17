package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.UserInformationMapper;
import com.ershou.bishe_demo1.Pojo.UserInformation;
import com.ershou.bishe_demo1.Service.UserInformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wsk1103 on 2017/4/26.
 */
@Service
public class UserInformationServiceImpl implements UserInformationService {
    @Resource
    private UserInformationMapper userInformationMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(UserInformation record) {
        return this.userInformationMapper.insert(record);
    }

    @Override
    public int insertSelective(UserInformation record) {
        return this.userInformationMapper.insertSelective(record);
    }

    @Override
    public UserInformation selectByPrimaryKey(Integer id) {
        return this.userInformationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserInformation> selectAll() { return this.userInformationMapper.selectAll();}

    @Override
    public int updateByPrimaryKeySelective(UserInformation record) {
        return this.userInformationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserInformation record) {
        return this.userInformationMapper.updateByPrimaryKey(record);
    }

    @Override
    public int selectIdByPhone(String phone) {
        try {
            return this.userInformationMapper.selectIdByPhone(phone);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int selectIdByUsername(String username) {
        try {
            return this.userInformationMapper.selectIdByUsername(username);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public UserInformation selectByUsername(String username) {
        try {
            int uid = this.userInformationMapper.selectIdByUsername(username);
            return this.userInformationMapper.selectByPrimaryKey(uid);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<UserInformation> getAllForeach(List<Integer> list) {
        return this.userInformationMapper.getAllForeach(list);
    }
}
