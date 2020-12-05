package com.diao.apipassenger.dao;

import com.diao.apipassenger.entity.EventPay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface EventPayDao {
    int deleteByPrimaryKey(Long id);

    int insert(EventPay record);

    int insertSelective(EventPay record);

    EventPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventPay record);

    int updateByPrimaryKey(EventPay record);

    List<EventPay> selectByState(Integer state);
}