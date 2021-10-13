package com.cloud.gen.service;


import com.cloud.gen.bean.DataBaseInfo;

import java.util.List;

public interface DataBaseService {

    /**新增DataBase数据库连接*/
    Boolean save(DataBaseInfo baseInfo);

    /**修改DataBase数据库连接*/
    Boolean update(DataBaseInfo baseInfo);

    /**删除DataBase数据库连接*/
    Boolean delete(String id);

    /**获取所有DataBase数据库连接信息*/
    List<DataBaseInfo> getAll();

    /**根据id获取DataBase数据连接信息*/
    DataBaseInfo getById(String id);
}
