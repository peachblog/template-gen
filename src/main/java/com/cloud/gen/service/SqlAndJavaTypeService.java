package com.cloud.gen.service;


import com.cloud.gen.bean.SqlAndJavaTypeInfo;

import java.util.List;

public interface SqlAndJavaTypeService {


    /**新增SqlAndJavaTypeInfo字段对于类型*/
    Boolean save(SqlAndJavaTypeInfo sqlAndJavaTypeInfo);

    /**修改SqlAndJavaTypeInfo字段对于类型*/
    Boolean update(SqlAndJavaTypeInfo sqlAndJavaTypeInfo);

    /**删除SqlAndJavaTypeInfo字段对于类型*/
    Boolean delete(String id);

    /**获取所有SqlAndJavaTypeInfo字段对于类型*/
    List<SqlAndJavaTypeInfo> getAll();

    /**根据id获取SqlAndJavaTypeInfo字段对于类型*/
    SqlAndJavaTypeInfo getById(String id);
}
