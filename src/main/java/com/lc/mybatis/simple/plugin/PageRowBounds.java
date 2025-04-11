package com.lc.mybatis.simple.plugin;

import org.apache.ibatis.session.RowBounds;

/**
 * 可以记录total的分页参数
 *
 * @author Mike
 * @date 2025/03/27
 */
public class PageRowBounds extends RowBounds {

    private long total;

    public PageRowBounds() {
        super();
    }

    public PageRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}