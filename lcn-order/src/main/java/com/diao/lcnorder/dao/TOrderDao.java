package com.diao.lcnorder.dao;

import com.diao.lcnorder.entity.TOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    TOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);

    int updateOrderStatus(Integer id, Integer status);
}