package com.cloud.gen.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class SqlAndJavaTypeInfo implements Serializable {

    private String id;
    /**JAVA类型*/
    private String javaType = "String";
    /**数据库类型*/
    private String sqlType;
    /**导入包*/
    private String pack;

}
