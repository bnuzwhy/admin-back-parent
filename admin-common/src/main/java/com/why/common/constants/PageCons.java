package com.why.common.constants;

/**
 * @description: PAGE 常量
 * @author: wanghongyu | stan.wang@paytm.com
 * @create: 2021/6/25
 **/
public interface PageCons {

    /**
     * 当前记录起始索引
     */
    String PAGE_NUM = "_page_num";

    /**
     * 每页显示记录数
     */
    String PAGE_SIZE = "_page_size";
    /**
     * 查询总数
     */
    String SEARCH_COUNT = "searchCount";
    /**
     * 排序列
     */
    String PAGE_SORT = "_page_sort";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    String PAGE_ORDER = "_page_order";

    /**
     * 默认每页条目20
     */
    int DEFAULT_PAGE_SIZE = 20;
    /**
     * 最大条目数100
     */
    int MAX_PAGE_SIZE = 100;

}
