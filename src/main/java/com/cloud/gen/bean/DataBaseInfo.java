package com.cloud.gen.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataBaseInfo implements Serializable {
    /**数据库别名*/
    private String name;
    /**主键标识*/
    private String id;
    /**数据库IP地址*/
    private String ip;
    /**数据库连接端口*/
    private String post;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**数据库名称*/
    private String dataBaseName;
}
