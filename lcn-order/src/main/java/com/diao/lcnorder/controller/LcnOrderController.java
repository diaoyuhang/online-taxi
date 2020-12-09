package com.diao.lcnorder.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.diao.lcnorder.dao.TOrderDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 14:34 2020/12/8
 */
@RestController
@RequestMapping("/order")
public class LcnOrderController {

    @Resource
    private TOrderDao tOrderDao;


    @RequestMapping("/updateOrder")
    @Transactional
    @LcnTransaction
    public String updateOrder(Integer id) {
        int result = tOrderDao.updateOrderStatus(id, 2);
        return "success";
    }
}
