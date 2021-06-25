package com.why.controller.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.why.common.page.PageDo;
import com.why.common.page.TableData;
import com.why.model.User;
import com.why.common.utils.TypeUtils;

import java.util.List;

/**
 * @description: 常规操作，统一分页处理
 * @author: Stan | stan.wang@paytm.com
 * @create: 2021/1/26 6:30 下午
 **/
public class WebController<Entity> extends SuperController<Entity>{
    /**
     * Step 1. 封装分页对象
     * @return
     */
    protected PageDo getPageDo(){
        // 页码
        Integer pageNum = TypeUtils.castToInt(request.getParameter(PAGE_NUM), 1);
        // 每页大小，默认20
        Integer pageSize = TypeUtils.castToInt(request.getParameter(PAGE_SIZE), DEFAULT_PAGE_SIZE);
        //是否启用分页查询，默认为true
        Boolean searchCount = TypeUtils.castToBoolean(request.getParameter(SEARCH_COUNT), true);
        //每页最多100
        pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        PageDo pageDo = new PageDo();
        pageDo.setPageNum(pageNum);
        pageDo.setPageSize(pageSize);
        pageDo.setSort(request.getParameter(PAGE_SORT));
        pageDo.setOrder(request.getParameter(PAGE_ORDER));
        pageDo.setTableAlias(getAlias());
        pageDo.setSearchCount(searchCount);
        return pageDo;
    }

    /**
     * Step 2. 设置请求分页数据
     */
    protected void startPage(){
        PageDo pageDo = getPageDo();
        Integer pageNum = pageDo.getPageNum();
        Integer pageSize = pageDo.getPageSize();
        boolean searchCount = pageDo.isSearchCount();
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum, pageSize, searchCount).setOrderBy(pageDo.getOrderBy());
        }
    }

    /**
     * 响应请求分页数据
     * @param list
     * @param <T>
     * @return
     */
    protected <T>TableData<T> getTableData(List<T> list){
        TableData td = new TableData();
        td.setRows(list);
        td.setTotal(new PageInfo(list).getTotal());
        return td;
    }

    //一下内容需要shiro，暂时默认替代
    public User getSysUser() {
        //return ShiroUtils.getSysUser();
        return new User();
    }

    public void setSysUser(User user) {
        //ShiroUtils.setSysUser(user);
    }

    public Long getUserId() {
        return getSysUser().getUserId();
    }

    public String getLoginName() {
        return getSysUser().getLoginName();
    }
}
