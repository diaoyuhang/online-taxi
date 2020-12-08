package com.diao.lcnpay.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.diao.lcnpay.dao.TPayDao;
import com.diao.lcnpay.entity.TPay;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 13:50 2020/12/8
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private TPayDao tPayDao;

    @RequestMapping("/payOrder")
    @Transactional
    @LcnTransaction
    public String payOrder(Integer orderId){
        TPay tPay = tPayDao.selectByOrderId(orderId);
        tPay.setStatus(2);
        tPayDao.updateByPrimaryKey(tPay);

        String result = restTemplate.getForObject("http://localhost:8081/order/updateOrder?id={1}", String.class, 1);
//        int i=1/0;
        return "success";
    }
}
