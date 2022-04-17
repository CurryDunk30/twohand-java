package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.OrderFormMapper;
import com.ershou.bishe_demo1.Pojo.OrderForm;
import com.ershou.bishe_demo1.Service.OrderFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wsk1103 on 2017/5/13.
 */
@Service
public class OrderFormServiceImpl implements OrderFormService {
    @Resource
    private OrderFormMapper orderFormMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(OrderForm record) {
        return orderFormMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderForm record) {
        return orderFormMapper.insertSelective(record);
    }

    @Override
    public OrderForm selectByPrimaryKey(Integer id) {
        return orderFormMapper.selectByPrimaryKey(id);
    }

    @Override
    public OrderForm selectUserNew(Integer uid) {
        try{
            return orderFormMapper.selectUserNew(uid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateByPrimaryKeySelective(OrderForm record) {
        return orderFormMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderForm record) {
        return orderFormMapper.updateByPrimaryKey(record);
    }

    @Override
    public int getCounts(int uid) {
        return orderFormMapper.getCounts(uid);
    }

    @Override
    public int getCountsByState(int uid, Integer state, Integer paystate) {
        try {
            return orderFormMapper.getCountsByState(uid, state, paystate);
        } catch ( Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getSellerCountsByState(int sellerId, Integer state, Integer paystate) {
        try {
            return orderFormMapper.getSellCountsByState(sellerId, state, paystate);
        } catch ( Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getSearchCountByStateAndPayState(Integer state, Integer paystate) {
        try {
            return orderFormMapper.getSearchCountByStateAndPayState(state, paystate, "");
        } catch ( Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<OrderForm> selectByUid(int uid, int start) {
        return orderFormMapper.selectByUid(uid, start);
    }

    @Override
    public List<OrderForm> selectByUid(int uid, int page, int size, Integer state, Integer paystate) {
        Integer start = (page-1) * size;
        try{

            return orderFormMapper.selectByUidAndState(uid, start, size, state, paystate);

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderForm> selectBySellerId(int sellerId, int page, int size, Integer state, Integer paystate) {
        Integer start = (page-1) * size;
        try{

            return orderFormMapper.selectSellerByUidAndState(sellerId, start, size, state, paystate);

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderForm> selectByPaystateAndState(int page, int size, Integer state, Integer paystate) {
        Integer start = (page-1) * size;
        try{

            return orderFormMapper.selectByPaystateAndState(start, size, state, paystate, "");

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
