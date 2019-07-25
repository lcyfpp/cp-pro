package com.linkedaim.admin.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyangyang
 * @title 分页响应
 * @date 2019-07-18
 */
public class PageResBean<T> implements Serializable {
    /**
     * 第几页
     */
    private Integer page = 0;
    /**
     * 总记录数
     */
    private Integer total = -1;
    /**
     * 总页数
     */
    private Integer totalPage = -1;
    /**
     * 每页记录数
     */
    private Integer pageSize = -1;
    /**
     * 数据列表
     */
    private List<T> list;

    public PageResBean() {
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        if (this.totalPage > 0) {
            return this.totalPage;
        } else if (this.total < 0) {
            return -1;
        } else {
            int count = this.total / this.pageSize;
            if (this.total % this.pageSize > 0) {
                ++count;
            }

            return count;
        }
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
