package com.why.common.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 表格分页数据对象
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 8:43 下午
 **/
@Getter@Setter
public class TableData<T> implements Serializable {
    private static final long serialVersionUID = 775468472514717490L;

    /**
     * 总记录数
     */
    private long total;
    /**
     * 列表数据
     */
    private List<T> rows;

    /**
     * 表格数据对象
     */
    public TableData() {
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableData(List<T> list, int total) {
        this.rows = list;
        this.total = total;
    }
}
