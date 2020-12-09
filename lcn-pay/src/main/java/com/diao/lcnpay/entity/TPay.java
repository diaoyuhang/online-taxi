package com.diao.lcnpay.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * t_pay
 * @author 
 */
@Data
public class TPay implements Serializable {
    private Integer id;

    private Integer orderid;

    private Integer status;

    private static final long serialVersionUID = 1L;
}