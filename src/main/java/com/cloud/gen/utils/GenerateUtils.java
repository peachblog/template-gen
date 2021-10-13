package com.cloud.gen.utils;

import cn.hutool.core.util.ObjectUtil;
import com.cloud.gen.bean.CustomInfo;
import com.cloud.gen.bean.SqlAndJavaTypeInfo;
import com.cloud.gen.bean.TableBean;
import com.cloud.gen.bean.TemplateInfo;
import com.cloud.gen.service.CustomService;
import com.cloud.gen.service.SqlAndJavaTypeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成工具类
 * */
@Component
public class GenerateUtils {
    @Resource
    public void setCustomService(CustomService customService) {
        this.customService = customService;
    }
    @Resource
    public void setSqlAndJavaTypeService(SqlAndJavaTypeService sqlAndJavaTypeService) {
        GenerateUtils.sqlAndJavaTypeService = sqlAndJavaTypeService;
    }

    private static CustomService customService;
    private static SqlAndJavaTypeService sqlAndJavaTypeService;

    /**
     * 生成指定模板指定表生成信息及对应
     * */
    public void generate(TableBean tableBean, List<TemplateInfo> templateInfos){
        Map<String, SqlAndJavaTypeInfo> map = new HashMap<>();
        sqlAndJavaTypeService.getAll().stream().forEach(sqlAndJavaTypeInfo -> map.put(sqlAndJavaTypeInfo.getSqlType(),sqlAndJavaTypeInfo));
        // 先装配bean
        tableBean.getTableFiledBeans().stream().forEach(tableFiledBean -> tableFiledBean.setType(map));
        List<CustomInfo> all = customService.getAll();
        DataSourceBeanUtils.init(tableBean, ObjectUtil.cloneByStream(all));
        // 生成
        DataSourceBeanUtils.templateGenerate(templateInfos);
    }

}
