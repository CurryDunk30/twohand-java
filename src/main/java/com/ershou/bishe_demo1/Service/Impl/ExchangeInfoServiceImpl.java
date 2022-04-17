package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.ExchangeInfoMapper;
import com.ershou.bishe_demo1.Pojo.ExchangeInfo;
import com.ershou.bishe_demo1.Pojo.ShopInformation;
import com.ershou.bishe_demo1.Service.ExchangeInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExchangeInfoServiceImpl implements ExchangeInfoService {
    @Resource
    ExchangeInfoMapper exchangeInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            return exchangeInfoMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    @Override
    public int deleteBySellerSid(Integer id) {
        try {
            return exchangeInfoMapper.deleteBySellerSid(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insert(ExchangeInfo record) {
        try {
            return exchangeInfoMapper.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insertSelective(ExchangeInfo record) {
        try {
            return exchangeInfoMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ExchangeInfo selectByPrimaryKey(Integer id) {
        try {
            return exchangeInfoMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateByPrimaryKeySelective(ExchangeInfo record) {
        try {
            return exchangeInfoMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateByPrimaryKey(ExchangeInfo record) {
        try {
            return exchangeInfoMapper.updateByPrimaryKey(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ExchangeInfo> selectBySeller(Integer sellerUid, Integer state, Integer page, Integer size) {
        Integer start = (page-1) * size;
        if(start<0) start = 0;
        try {
            return exchangeInfoMapper.selectBySellerAndStatePage(sellerUid, state, start, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExchangeInfo> selectBySeller(Integer sellerUid, Integer state) {
        try {
            return exchangeInfoMapper.selectBySellerAndState(sellerUid, state);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExchangeInfo> selectByExchange(Integer exchangeUid, Integer state, Integer page, Integer size) {
        Integer start = (page-1) * size;
        if(start<0) start = 0;
        try {
            return exchangeInfoMapper.selectByExchangeAndStatePage(exchangeUid, state, start, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExchangeInfo> selectByExchange(Integer exchangeUid, Integer state) {
        try {
            return exchangeInfoMapper.selectByExchangeAndState(exchangeUid, state);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExchangeInfo> selectByKind(Integer kind) {
        try {
            return exchangeInfoMapper.selectByKind(kind);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCountsByExchange(Integer exchangeUid, Integer state, String name) {
        try {
            return exchangeInfoMapper.getExchangeStateCounts(exchangeUid, state, name);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountsByExchange(Integer exchangeUid) {
        try {
            return exchangeInfoMapper.getCountsByExchange(exchangeUid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountsBySeller(Integer sellerUid, Integer state, String name) {
        try {
            return exchangeInfoMapper.getSellerStateCounts(sellerUid, state, name);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountsBySeller(Integer sellerUid) {
        try {
            return exchangeInfoMapper.getCountsBySeller(sellerUid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
