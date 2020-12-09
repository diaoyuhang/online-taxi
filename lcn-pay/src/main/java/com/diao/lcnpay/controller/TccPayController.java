package com.diao.lcnpay.controller;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.diao.lcnpay.dao.TPayDao;
import com.diao.lcnpay.entity.TPay;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 9:01 2020/12/9
 */
@RestController
@RequestMapping("/tccPay")
public class TccPayController {

    private Map<Object, Object> map = new HashMap<>();

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private TPayDao tPayDao;

    @RequestMapping("/payOrder")
    @Transactional
    @TccTransaction
    public String payOrder(Integer orderId) {
        System.out.println("thread====" + Thread.currentThread().getId());
        TPay tPay = tPayDao.selectByOrderId(orderId);
        map.put("payOrder_status",tPay.getStatus());

        tPay.setStatus(2);
        tPayDao.updateByPrimaryKey(tPay);

        String result = restTemplate.getForObject("http://localhost:8081/tccOrder/updateOrder?id={1}", String.class, 1);
        int i=1/0;
        return "success";
    }


    public String confirmPayOrder(Integer orderId) {
        System.out.println("confirm thread=====" + Thread.currentThread().getId());
        System.out.println("====支付成功");
        map.remove("payOrder_status");
        return "支付成功";
    }

    public String cancelPayOrder(Integer orderId) {
        System.out.println("cancel thread=====" + Thread.currentThread().getId());
        Object o = map.get("payOrder_status");

        int result = tPayDao.updateByOrderId(orderId, (Integer) o);

        return "回滚数据";
    }
}
