package com.cloud.gen.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.cloud.gen.bean.*;
import com.cloud.gen.service.SqlService;
import com.cloud.gen.service.TemplateService;
import com.cloud.gen.service.impl.GenerationListenDefault;
import com.cloud.gen.utils.GenerateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


    public <T> List<T> copyList(List<T> list,Class<T> tClass){
        ArrayList<T> objects = new ArrayList<>(list.size());
        list.stream().forEach(o -> {
            objects.add(BeanUtil.toBean(o,tClass));
        });
        return objects;
    }


    @PostMapping("gen")
    public boolean gen(@RequestBody GenerateBean generateBean){
        List<TemplateInfo> all = templateService.getAll();
        Map<String, TemplateInfo> collect = generateBean.getTemplateInfos().stream().collect(Collectors.toMap(TemplateInfo::getId, templateInfo -> templateInfo));
        all.stream().forEach(templateInfo -> {
            if(collect.containsKey(templateInfo.getId())){
                TemplateInfo templateInfo1 = collect.get(templateInfo.getId());
                templateInfo.setPath(templateInfo1.getPath());
                templateInfo.setPackageName(templateInfo1.getPackageName());
                templateService.update(templateInfo);
            }
        });
        generateBean.getTableBeans().stream().forEach(tableBean -> {
            generateUtils.generate(tableBean, copyList(generateBean.getTemplateInfos(),TemplateInfo.class).stream().filter(templateInfo -> templateInfo.getAsGen()).collect(Collectors.toList()));
            GenerationListenDefault.getSINGLE().run(tableBean,generateBean.getTemplateInfos());
        });
        return true;
    }
}
