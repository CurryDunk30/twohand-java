package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.OrderForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderForm record);

    int insertSelective(OrderForm record);

    OrderForm selectByPrimaryKey(Integer id);
    OrderForm selectUserNew(Integer uid);

    int updateByPrimaryKeySelective(OrderForm record);

    int updateByPrimaryKey(OrderForm record);

    int getCounts(int uid);
    int getCountsByState(Integer uid, Integer state, Integer paystate);
    int getSellCountsByState(Integer sellerId, Integer state, Integer paystate);

    int getSearchCountByStateAndPayState(Integer state, Integer paystate, String address);

    List<OrderForm> selectByUid(int uid, int start);
    List<OrderForm> selectByUidAndState(Integer uid, Integer start, Integer size, Integer state, Integer paystate);
    List<OrderForm> selectSellerByUidAndState(Integer sellerId, Integer start, Integer size, Integer state, Integer paystate);
    List<OrderForm> selectByPaystateAndState(Integer start, Integer size, Integer state, Integer paystate, String address);
}