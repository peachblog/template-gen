package com.cloud.gen.controller;


import cn.hutool.core.util.StrUtil;
import com.cloud.gen.bean.TemplateInfo;
import com.cloud.gen.enums.ComposeEnums;
import com.cloud.gen.service.TemplateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1/template")
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @RequestMapping("all")
    public List<TemplateInfo> getAll(){
        //现获取当前的路径
        List<TemplateInfo> all = templateService.getAll();
        all.stream().forEach(templateInfo -> templateInfo.setReFileName(ComposeEnums.TEMPLATE.before + templateInfo.getReFileNameName() + ComposeEnums.TEMPLATE.after));
        return all;
    }

    @RequestMapping("genAll")
    public List<TemplateInfo> genAll(){
        //现获取当前的路径
        List<TemplateInfo> all = templateService.getAll();
        String property = System.getProperty("user.dir");
        all.stream().forEach(templateInfo -> {
            templateInfo.setRootPath(property);
            templateInfo.setPath(StrUtil.isNotEmpty(templateInfo.getPath())?(templateInfo.getPath().contains(property)?templateInfo.getPath():property + templateInfo.getPath()): templateInfo.getPath());
            templateInfo.setAsPath(false);
            templateInfo.setAsDel(false);
            templateInfo.setAsGen(true);
            templateInfo.setReFileName(ComposeEnums.TEMPLATE.before + templateInfo.getReFileNameName() + ComposeEnums.TEMPLATE.after);
        });
        return all;
    }

    @PostMapping("save")
    public Boolean save(@RequestBody TemplateInfo templateInfo){
        return templateService.save(templateInfo);
    }

    @PostMapping("update")
    public Boolean update(@RequestBody TemplateInfo templateInfo){
        return templateService.update(templateInfo);
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody TemplateInfo templateInfo){
        return templateService.delete(templateInfo.getId());
    }
}
