package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.UserAddressMapper;
import com.ershou.bishe_demo1.Mapper.UserInformationMapper;
import com.ershou.bishe_demo1.Mapper.UserPasswordMapper;
import com.ershou.bishe_demo1.Mapper.UserStateMapper;
import com.ershou.bishe_demo1.Pojo.*;
import com.ershou.bishe_demo1.Service.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserInformationMapper userInformationMapper;

    @Resource
    private UserPasswordMapper userPasswdMapper;

    @Resource
    private UserStateMapper userStateMapper;

    @Resource
    private UserAddressMapper userAddressMapper;


    @Override
    public boolean SignUp(Integer uid, String password) {
        UserPassword userPass = new UserPassword();
        userPass.setUid(uid);
        userPass.setPassword(password);

        //        已经判断过username没有使用
        try {
            userPasswdMapper.insert(userPass);
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        UserState userState = new UserState();
        userState.setBalance(new BigDecimal(0));
        userState.setCredit(0);
        userState.setUid(uid);
        try{
            userStateMapper.insert(userState);
            return true;
        } catch (Exception e) {
            userInformationMapper.deleteByPrimaryKey(uid);
            userPasswdMapper.deleteByPrimaryKey(uid);
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean CheckUserPasswd(String username, String password) {
        try {
            Integer uid = userInformationMapper.selectIdByUsername(username);

//        通过用户id获取用户的密码
            UserPassword userPassword = userPasswdMapper.selectByUid(uid);

            return password.equals(userPassword.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getSearchCountByRole(String searchContent, int role, String searchType){
        int nums = 0;
        switch (searchType){
            case "username": {
                nums = userInformationMapper.getCountsBySearchUsername(searchContent, role);
                break;
            }
            case "realname": {
                nums = userInformationMapper.getCountsBySearchRealname(searchContent, role);
                break;
            }
            case "nickname": {
                nums = userInformationMapper.getCountsBySearchNickname(searchContent, role);
                break;
            }
            case "": {
                nums = userInformationMapper.getCountsByRole(role);
                break;
            }
            default: {
                return -1;
            }
        }
        return nums;
    }

    @Override
    public List<UserInformation> searchUserPageByRole(int page, int size, String searchContent, int role, String searchType){
        List<UserInformation> result;
        Integer start = (page-1) * size;
        switch (searchType){
            case "username": {
                result = userInformationMapper.SearchByUsername(start, size, searchContent, role);
                break;
            }
            case "realname": {
                result = userInformationMapper.SearchByRealname(start, size, searchContent, role);
                break;
            }
            case "nickname": {
                result = userInformationMapper.SearchByNickname(start, size, searchContent, role);
                break;
            }
            case "": {
                result = userInformationMapper.SearchByRole(start, size, role);
                break;
            }
            default: {
                return null;
            }
        }
        return result;
    }

    @Override
    public boolean ChangeUserPasswd(Integer uid, UserPassword record) {
        return false;
    }

    @Override
    public UserInformation GetUserInfo(Integer uid) {
        return null;
    }

    @Override
    public boolean ModifyUserInfo(Integer uid, UserInformation user) {
        return false;
    }

    @Override
    public UserState GetUserState(Integer uid) {
        return null;
    }

    @Override
    public boolean ModifyBalance(Integer uid, BigDecimal balance) {
        return false;
    }

    @Override
    public String GetUserLikes(Integer uid) {
        return null;
    }

    @Override
    public boolean DeleteUserLike(Integer uid, Integer sid) {
        return false;
    }

    @Override
    public boolean AddUserLike(Integer uid, Integer sid) {
        return false;
    }

    @Override
    public String GetUserAllOrder(Integer uid) {
        return null;
    }

    @Override
    public boolean DeleteUserOrder(Integer uid, Integer sid) {
        return false;
    }

    @Override
    public boolean AddUserOrder(Integer uid, Integer sid) {
        return false;
    }

    @Override
    public boolean UpdateUserOrder(OrderForm order) {
        return false;
    }

    @Override
    public String GetUserShopCar(Integer uid) {
        return null;
    }

    @Override
    public boolean DeleteShopCarByNum(Integer uid, Integer sid, Integer num) {
        return false;
    }

    @Override
    public boolean AddShop(Integer uid, ShopInformation shop) {
        return false;
    }

    @Override
    public boolean UpdateShopCarByNum(Integer uid, Integer sid, Integer num) {
        return false;
    }

    @Override
    public String GetUserWantShops(Integer uid) {
        return null;
    }

    @Override
    public boolean DeleteWantShop(Integer uid, Integer wid, Integer num) {
        return false;
    }

    @Override
    public boolean AddWantShop(Integer uid, WantShop wshop) {
        return false;
    }

    @Override
    public boolean UpdateWantShop(Integer uid, Integer wid, Integer num) {
        return false;
    }

    @Override
    public String GetUserShopOrder(Integer uid) {
        return null;
    }

    @Override
    public boolean DeleteUserShopOrder(Integer uid, Integer sid) {
        return false;
    }

    @Override
    public boolean UpdateUserShopOrder(OrderForm order) {
        return false;
    }

    @Override
    public List<UserAddress> GetUserAddress(Integer uid) {
        try {
            return userAddressMapper.selectByUid(uid);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public List<UserAddress> GetUserDefaultAddress(Integer uid) {
        try {
            return userAddressMapper.selectDefaultByUid(uid);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public List<UserAddress> GetUserAddressByUsername(String username) {
        try{
            int uid = userInformationMapper.selectIdByUsername(username);
            if(userAddressMapper.getCounts(uid) > 0) {
                return userAddressMapper.selectByUid(uid);
            }else{
                return null;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean AddUserAddress(UserAddress addr) {
        try {
            int result = userAddressMapper.insert(addr);
            return result > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ModifyUserAddress(UserAddress addr) {
        try {
            //  一定要有id的信息
            int result = userAddressMapper.updateByPrimaryKeySelective(addr);
            return result > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteUserAddress(Integer id) {
        try {
            //  一定要有id的信息
            int result = userAddressMapper.deleteByPrimaryKey(id);
            return result > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
