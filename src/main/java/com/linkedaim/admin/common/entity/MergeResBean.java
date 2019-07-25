package com.linkedaim.admin.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 数据聚合合并
 * @date 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MergeResBean<T> extends PageResBean<T> implements Serializable {
    /**
     * 数据合并信息
     */
    private T mergeData;
}
