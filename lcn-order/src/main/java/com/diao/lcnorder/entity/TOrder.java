package com.diao.lcnorder.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * t_order
 * @author 
 */
@Data
public class TOrder implements Serializable {
    private Integer id;

    private String name;

    private Integer status;

    private static final long serialVersionUID = 1L;
}