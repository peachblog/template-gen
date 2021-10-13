package com.cloud.gen.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 生成实体
 * */
@Data
public class GenerateBean implements Serializable {

    /**
     * 生成表
     * */
    private List<TableBean> tableBeans;
    /**
     * 生成模板
     * */
    private List<TemplateInfo> templateInfos;
}
