package com.why.common.page;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.why.common.sql.AntiSqlFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

/**
 * @description: 分页字段--分页，排序
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 8:36 下午
 **/
@Getter@Setter
public class PageDo {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String sort;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String order;
    /**
     * 排序表别名
     */
    private String tableAlias;
    /**
     * 查询Count
     */
    private boolean searchCount;

    public String getOrderBy(){
        if(ObjectUtils.isEmpty(sort)){
            return "";
        }
        if(!ObjectUtils.isEmpty(tableAlias)){
            return tableAlias + "."+ StringUtils.underlineToCamel(sort)+" "+order;
        }
        return StringUtils.underlineToCamel(sort)+ " "+order;
    }

    public String getSort(){
        return AntiSqlFilter.getSafeValues(sort);
    }
}
