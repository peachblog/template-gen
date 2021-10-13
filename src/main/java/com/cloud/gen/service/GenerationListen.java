package com.cloud.gen.service;

import com.cloud.gen.bean.TableBean;
import com.cloud.gen.bean.TemplateInfo;

import java.util.List;

/**
 * 生成工具监听
 * */
public interface GenerationListen {
    /**
     * 生成一套代码进行监听
     * @param tableBean 生成表信息
     * @param list 生成模板信息
     * */
    void run(TableBean tableBean, List<TemplateInfo> list);
}
