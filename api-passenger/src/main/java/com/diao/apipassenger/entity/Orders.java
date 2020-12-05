package com.diao.apipassenger.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * orders
 * @author 
 */
@Data
public class Orders implements Serializable {
    private String id;

    private Integer state;

    private static final long serialVersionUID = 1L;
}