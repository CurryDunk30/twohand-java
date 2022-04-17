package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.*;

import java.math.BigDecimal;
import java.util.List;

// 定义用户的事物
public interface UserService {
//    注册功能（传入uid及个人信息）
    boolean SignUp(Integer uid, String password);

//    验证密码
    boolean CheckUserPasswd(String username, String password);

//    修改密码
    boolean ChangeUserPasswd(Integer uid, UserPassword record);

//    获取个人信息
    UserInformation GetUserInfo(Integer uid);

//    修改个人信息
    boolean ModifyUserInfo(Integer uid, UserInformation user);

//    获取账户信息
    UserState GetUserState(Integer uid);

//    修改用户账户信息（仅可以增加balance）
    boolean ModifyBalance(Integer uid, BigDecimal balance);

//    我收藏的物品 CRD
    String GetUserLikes(Integer uid);
    boolean DeleteUserLike(Integer uid, Integer sid);
    boolean AddUserLike(Integer uid, Integer sid);

//    我的订单 CRUD
    String GetUserAllOrder(Integer uid);
    boolean DeleteUserOrder(Integer uid, Integer sid);
    boolean AddUserOrder(Integer uid, Integer sid);
//    买家
    boolean UpdateUserOrder(OrderForm order);

//    我的购物车 CRUD
    String GetUserShopCar(Integer uid);
    boolean DeleteShopCarByNum(Integer uid, Integer sid, Integer num);
    boolean AddShop(Integer uid, ShopInformation shop);
    boolean UpdateShopCarByNum(Integer uid, Integer sid, Integer num);

//    管理发布的商品 CRUD
    String GetUserWantShops(Integer uid);
    boolean DeleteWantShop(Integer uid, Integer wid, Integer num);
    boolean AddWantShop(Integer uid, WantShop wshop);
    boolean UpdateWantShop(Integer uid, Integer wid, Integer num);


//    我发的帖子 CRUD（暂时放着）


//    管理订单 CRUD
    String GetUserShopOrder(Integer uid);
    boolean DeleteUserShopOrder(Integer uid, Integer sid);
//    boolean AddUserShopOrder(Integer uid, Integer sid);
//    卖家修改
    boolean UpdateUserShopOrder(OrderForm order);

//    获取用户收货地址
    List<UserAddress> GetUserAddress(Integer uid);
    List<UserAddress> GetUserDefaultAddress(Integer uid);
    List<UserAddress> GetUserAddressByUsername(String username);
    boolean AddUserAddress(UserAddress addr);
    boolean ModifyUserAddress(UserAddress addr);
    boolean DeleteUserAddress(Integer id);

    int getSearchCountByRole(String searchContent, int role, String searchType);
    List<UserInformation> searchUserPageByRole(int page, int size, String searchContent, int role, String searchType);
}
