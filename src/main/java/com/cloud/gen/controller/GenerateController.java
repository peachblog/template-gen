package com.cloud.gen.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.gen.bean.*;
import com.cloud.gen.service.SqlService;
import com.cloud.gen.service.TemplateService;
import com.cloud.gen.service.impl.GenerationListenDefault;
import com.cloud.gen.utils.GenerateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/generate")
public class GenerateController {

    @Resource
    private SqlService sqlService;

    @Resource
    private TemplateService templateService;

    @PostMapping("tableAll")
    public List<TableBean> tableAll(@RequestBody DataBaseInfo dataBaseInfo){
        sqlService.createDataBase(dataBaseInfo);
        List<TableBean> tableByDataBase = sqlService.getTableByDataBase();
        return tableByDataBase;
    }

    @GetMapping("tableFiled/{tableName}")
    public List<TableFiledBean> tableFiled(@PathVariable String tableName){
        List<TableFiledBean> sqlEntity = sqlService.getSqlEntity(tableName);
        return sqlEntity;
    }

    @Resource
    private GenerateUtils generateUtils;

    @PostMapping("gen")
    public boolean gen(@RequestBody GenerateBean generateBean){
        generateBean.getTemplateInfos().stream().forEach(templateInfo -> {
            TemplateInfo templateInfo1 = BeanUtil.toBean(templateInfo, TemplateInfo.class);
            templateInfo1.setPath(templateInfo1.getPath().replace(templateInfo.getRootPath(),""));
            templateInfo1.setRootPath(null);
            templateService.update(templateInfo1);
        });
        generateBean.getTableBeans().stream().forEach(tableBean -> {
            generateUtils.generate(tableBean,generateBean.getTemplateInfos().stream().filter(templateInfo -> templateInfo.getAsGen()).collect(Collectors.toList()));
            GenerationListenDefault.getSINGLE().run(tableBean,generateBean.getTemplateInfos());
        });
        return true;
    }
}
