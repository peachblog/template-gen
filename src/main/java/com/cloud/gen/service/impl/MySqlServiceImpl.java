package com.cloud.gen.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.cloud.gen.bean.DataBaseInfo;
import com.cloud.gen.bean.TableBean;
import com.cloud.gen.bean.TableFiledBean;
import com.cloud.gen.service.SqlService;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MySqlServiceImpl implements SqlService {

    private DataBaseInfo dataBaseInfo = null;
    private Db db = null;

    @Override
    public void createDataBase(DataBaseInfo dataBaseInfo){
        String url = StrUtil.format("jdbc:mysql://{}:{}/{}?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai",dataBaseInfo.getIp(),dataBaseInfo.getPost(),dataBaseInfo.getDataBaseName());
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(dataBaseInfo.getUsername());
        dataSource.setPassword(dataBaseInfo.getPassword());
        this.dataBaseInfo = dataBaseInfo;
        db = new Db(dataSource);
    }

    @Override
    public List<TableBean> getTableByDataBase() {
        List<TableBean> list = new ArrayList<>();
        if (Objects.isNull(db)){
            return list;
        }
        try {
            List<Entity> query = db.query("select table_name,table_comment from information_schema.tables where table_schema='" + dataBaseInfo.getDataBaseName() + "'");
            query.stream().forEach(entity -> {
                TableBean tableBean = new TableBean();
                tableBean.setTableName(entity.getStr("table_name"));
                tableBean.setTableComment(entity.getStr("table_comment"));
                list.add(tableBean);
            });
        } catch (SQLException throwables) {
        }
        return list;
    }

    @Override
    public List<TableFiledBean> getSqlEntity(String name) {
        List<TableFiledBean> list = new ArrayList<>();
        if (Objects.isNull(db)){
            return list;
        }
        try {
            List<Entity> query = db.query("select * from information_schema.columns where table_name='" + name + "' AND table_schema='" + dataBaseInfo.getDataBaseName() + "'");
            query.stream().forEach(entity -> {
                TableFiledBean tableBean = new TableFiledBean();
                tableBean.setColumnName(StrUtil.toCamelCase(entity.getStr("column_name")));
                tableBean.setColumnDefault(entity.getStr("column_default"));
                tableBean.setAsNull(Objects.equals(entity.getStr("is_nullable"),"NO"));
                tableBean.setDataType(entity.getStr("data_type"));
                tableBean.setMaxLength(entity.getInt("character_maximum_length"));
                tableBean.setColumnKey(entity.getStr("column_key"));
                tableBean.setColumnComment(entity.getStr("column_comment"));
                tableBean.setNickName(tableBean.getColumnComment().length()>4?tableBean.getColumnComment().substring(0,4):tableBean.getColumnComment());
                list.add(tableBean);
            });
        } catch (SQLException throwables) {
        }
        return list;
    }

}
