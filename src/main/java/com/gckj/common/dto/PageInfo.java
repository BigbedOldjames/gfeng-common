package com.gckj.common.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description：分页信息
 * @author：ldc
 * date：2020-06-23
 */
public class PageInfo extends Page {

    public PageInfo convert(Object obj) {
        IPage page = (IPage) obj;
        this.setPageNumber((int) page.getCurrent());
        this.setPageSize((int) page.getSize());
        this.setCount(page.getTotal());
        this.setList(page.getRecords());
        return this;
    }
}
