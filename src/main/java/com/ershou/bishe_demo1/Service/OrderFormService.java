package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.OrderForm;

import java.util.List;

/**
 * Created by wsk1103 on 2017/5/13.
 */
public interface OrderFormService {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderForm record);

    int insertSelective(OrderForm record);

    OrderForm selectByPrimaryKey(Integer id);
    OrderForm selectUserNew(Integer uid);

    int updateByPrimaryKeySelective(OrderForm record);

    int updateByPrimaryKey(OrderForm record);

    int getCounts(int uid);

    int getCountsByState(int uid, Integer state, Integer paystate);
    int getSellerCountsByState(int sellerId, Integer state, Integer paystate);
    int getSearchCountByStateAndPayState(Integer state, Integer paystate);

    List<OrderForm> selectByUid(int uid, int start);

    List<OrderForm> selectByUid(int uid, int page, int size, Integer state, Integer paystate);
    List<OrderForm> selectBySellerId(int sellerId, int page, int size, Integer state, Integer paystate);
    List<OrderForm> selectByPaystateAndState(int page, int size, Integer state, Integer paystate);
}
