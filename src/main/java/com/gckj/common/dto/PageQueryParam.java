package com.gckj.common.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @Description：分页查询
 * @author：ldc
 * date：2020-06-23
 */
@Data
public class PageQueryParam<T> {

    private T query;
    /**
     * 每页显示条数
     */
    private int size;
    /**
     * 当前页
     */
    private int current;
    private List<OrderItem> orders;

    public Page<T> createPage() {
        Page<T> page = new Page();
        page.setSize(Objects.isNull(this.size) ? 10L : this.size);
        page.setCurrent(Objects.isNull(this.current) ? 1L : this.current);
        page.setOrders(this.orders);
        return page;
    }
}
