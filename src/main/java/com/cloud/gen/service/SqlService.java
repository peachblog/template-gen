package com.cloud.gen.service;



import com.cloud.gen.bean.DataBaseInfo;
import com.cloud.gen.bean.TableBean;
import com.cloud.gen.bean.TableFiledBean;

import java.util.List;

public interface SqlService {
    /**
     * 获取表信息
     * */
    List<TableBean> getTableByDataBase();

    /**
     * 获取表内容字段信息
     * */
    List<TableFiledBean> getSqlEntity(String name);

    /**
     * 创建数据连接
     * */
    void createDataBase(DataBaseInfo dataBase);
}
