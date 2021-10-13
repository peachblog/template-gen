package com.cloud.gen.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TableBean implements Serializable {
    /**表名*/
    private String tableName;
    /**备注信息*/
    private String tableComment;
    /**表字段信息*/
    private List<TableFiledBean> tableFiledBeans;
}
