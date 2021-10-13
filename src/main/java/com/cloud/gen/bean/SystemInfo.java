package com.cloud.gen.bean;

import java.util.ArrayList;
import java.util.List;

public class SystemInfo {
    private static String classification = "system";
    List<CustomInfo> system = new ArrayList<CustomInfo>(){{
        add(CustomInfo.builder().
                reFiledNameName("tableName").remark("表名替换（首字母大写骆驼峰）").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("mTableName").remark("表名替换（首字母小写骆驼峰）").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("_tableName").remark("表名替换（全小写下划线）").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("tableComment").remark("表注释替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("date").remark("当前日期").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("time").remark("当前时间").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("pack").remark("包替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("beanPack").remark("当前对象包替换").classification(classification).type(0).build());
    }};
    /**循环内部字段模块*/
    List<CustomInfo> systemFor = new ArrayList<CustomInfo>(){{
        add(CustomInfo.builder().
                reFiledNameName("javaFiled").remark("java字段替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("mJavaFiled").remark("首字母大写java字段替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("nickname").remark("别名昵称").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("sqlFiled").remark("sql字段替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("javaType").remark("java类型替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("Type").remark("sql类型替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("comment").remark("表注释替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("defaultV").remark("默认值替换").classification(classification).type(0).build());
        add(CustomInfo.builder().
                reFiledNameName("maxLength").remark("最长字符替换").classification(classification).type(0).build());
    }};

    public List<CustomInfo> getSystem() {
        return system;
    }

    public List<CustomInfo> getSystemFor() {
        return systemFor;
    }
}
