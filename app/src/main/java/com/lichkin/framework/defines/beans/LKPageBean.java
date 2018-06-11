package com.lichkin.framework.defines.beans;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页数据
 * @param <T> 数据类型
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LKPageBean<T> {

    /** 数据列表 */
    private List<T> content;

    /** 页码 */
    private int number;

    /** 当前页数据量 */
    private int numberOfElements;

    /** 每页数据量 */
    private int size;

    /** 总数据量 */
    private long totalElements;

    /** 总页数 */
    private int totalPages;

    /** 是否第一页 */
    private boolean first;

    /** 是否最后一页 */
    private boolean last;

    /**
     * 构造方法
     * @param content 数据列表
     * @param number 页码
     * @param size 每页数据量
     * @param totalElements 总数据量
     */
    public LKPageBean(List<T> content, int number, int size, long totalElements) {
        this.content = content;
        this.number = number;
        this.numberOfElements = content.size();
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) this.totalElements / this.size);
        this.first = number == 0;
        this.last = ((number + 1) * size) >= this.totalElements;
    }

}
