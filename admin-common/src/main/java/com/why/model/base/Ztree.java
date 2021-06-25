package com.why.model.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description:
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/25 7:31 下午
 **/
@Getter@Setter
public class Ztree implements Serializable {
    private static final long serialVersionUID = 2134958719603429283L;
    /**
     * 节点ID
     */
    private Long id;
    /**
     * 父节点ID
     */
    private Long pId;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点标题
     */
    private String title;

    /**
     * 是否勾选
     */
    private boolean checked = false;

    /**
     * 是否展开
     */
    private boolean open = false;

    /**
     * 是否能勾选
     */
    private boolean nocheck = false;
}
