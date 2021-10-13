package com.cloud.gen.service;

import com.cloud.gen.bean.TemplateInfo;

import java.util.List;

public interface TemplateService {

    /**新增TemplateInfo自定义模板信息*/
    Boolean save(TemplateInfo info);

    /**修改TemplateInfo自定义模板信息*/
    Boolean update(TemplateInfo info);

    /**删除TemplateInfo自定义模板信息*/
    Boolean delete(String id);

    /**获取所有TemplateInfo字段模板信息*/
    List<TemplateInfo> getAll();

    /**根据id获取TemplateInfo字段模板信息*/
    TemplateInfo getById(String id);
}
