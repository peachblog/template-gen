package com.cloud.gen.service.impl;

import java.io.File;

public abstract class BaseServiceImpl {

    /**
     * 获取配置路径
     * */
    protected String getPath(){
        String path = (this.getClass().getResource("").getPath() + "generate").replaceAll("target/classes","src/main/java");
        return path.substring(1) + File.separatorChar;
    }

}
