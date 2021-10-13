package com.cloud.gen.service;


import com.cloud.gen.bean.CustomInfo;

import java.util.List;

public interface CustomService {

    /**新增CustomInfo自定义字段*/
    Boolean save(CustomInfo info);

    /**修改CustomInfo自定义字段*/
    Boolean update(CustomInfo info);

    /**删除CustomInfo自定义字段*/
    Boolean delete(String id);

    /**获取所有CustomInfo自定义字段*/
    List<CustomInfo> getAll();

    /**根据id获取CustomInfo自定义字段*/
    CustomInfo getById(String id);
}
