package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.ShopContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopContextMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteBySidAndType(Integer sid, Integer commentType);

    int insert(ShopContext record);

    int insertSelective(ShopContext record);

    ShopContext selectByPrimaryKey(Integer id);
    List<ShopContext> selectByTypePage(Integer commentType, Integer start, Integer size);


    int updateByPrimaryKeySelective(ShopContext record);

    int updateByPrimaryKey(ShopContext record);

    int getCounts(int sid);
    int getCountsByType(Integer commentType);

    List<ShopContext> findById(int sid, int start);

    @Select("select * from comments where sid=#{id,jdbcType=INTEGER} and type=#{type, jdbcType=INTEGER} order by id DESC")
    List<ShopContext> selectBySid(int id, int type);
}