package com.diao.apipassenger.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * event_pay
 * @author 
 */
@Data
public class EventPay implements Serializable {
    private Long id;

    private String orderId;

    private Integer state;

    private static final long serialVersionUID = 1L;
}