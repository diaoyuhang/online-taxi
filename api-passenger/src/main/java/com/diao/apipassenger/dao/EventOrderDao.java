package com.diao.apipassenger.dao;

import com.diao.apipassenger.entity.EventOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(EventOrder record);

    int insertSelective(EventOrder record);

    EventOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventOrder record);

    int updateByPrimaryKey(EventOrder record);

    int insertEventOrder(EventOrder eventOrder);
}