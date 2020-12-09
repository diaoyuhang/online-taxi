package com.diao.lcnpay.dao;

import com.diao.lcnpay.entity.TPay;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TPayDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TPay record);

    int insertSelective(TPay record);

    TPay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPay record);

    int updateByPrimaryKey(TPay record);

    TPay selectByOrderId(Integer orderId);

    int updateByOrderId(Integer orderId, Integer status);
}