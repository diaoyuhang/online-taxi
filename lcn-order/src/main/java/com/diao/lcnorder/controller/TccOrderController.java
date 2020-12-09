package com.diao.lcnorder.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.diao.lcnorder.dao.TOrderDao;
import com.diao.lcnorder.entity.TOrder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 9:01 2020/12/9
 */
@RestController
@RequestMapping("/tccOrder")
public class TccOrderController {

    @Resource
    private TOrderDao tOrderDao;

    private Map<Object, Object> map = new HashMap<>();

    @RequestMapping("/updateOrder")
    @Transactional
    @TccTransaction
    public String updateOrder(Integer id, HttpServletRequest request) {
        System.out.println("thread====" + Thread.currentThread().getId());
        TOrder tOrder = tOrderDao.selectByPrimaryKey(id);
        map.put("updateOrder_status",tOrder.getStatus());

        int result = tOrderDao.updateOrderStatus(id, 2);
        return "success";
    }


    public String confirmUpdateOrder(Integer id,HttpServletRequest request) {
        System.out.println("confirm thread=====" + Thread.currentThread().getId());
        map.remove("updateOrder_status");
        return "订单状态修改成功";
    }

    public String cancelUpdateOrder(Integer id,HttpServletRequest request) {
        System.out.println("cancel thread=====" + Thread.currentThread().getId());
        Object o = map.get("updateOrder_status");
        tOrderDao.updateOrderStatus(id, (Integer) o);

        return "订单数据回滚";
    }
}
