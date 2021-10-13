package com.cloud.gen.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.cloud.gen.bean.TemplateInfo;
import com.cloud.gen.service.TemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl extends BaseServiceImpl implements TemplateService {
    private static final Log log = LogFactory.get();


    private String fileName = "template";
    private List<TemplateInfo> list;

    @Override
    public Boolean save(TemplateInfo info) {
        List<TemplateInfo> all = getAll();
        List<TemplateInfo> collect = all.stream().filter(templateInfo -> templateInfo.getReFileNameName().equals(info.getReFileNameName())).collect(Collectors.toList());
        if(collect.size()>0){
            return false;
        }
        info.setId(IdUtil.fastUUID());
        all.add(info);
        return write(all);
    }

    @Override
    public Boolean update(TemplateInfo info) {
        List<TemplateInfo> all = getAll();
        all.stream().forEach(info1 -> {
            if (Objects.equals(info1.getId(),info.getId())){
                BeanUtils.copyProperties(info,info1);
            }
        });
        return write(all);
    }

    @Override
    public Boolean delete(String id) {
        List<TemplateInfo> all = getAll();
        list = all.stream().filter(info -> !Objects.equals(info.getId(), id)).collect(Collectors.toList());
        return  write(list);
    }

    @Override
    public List<TemplateInfo> getAll() {
        if (Objects.isNull(list)){
            if (FileUtil.exist(new File(getPath() + fileName))){
                list = read();
            }else {
                list= new ArrayList<>();
            }
        }
        return list;
    }

    @Override
    public TemplateInfo getById(String id) {
        List<TemplateInfo> all = getAll();
        for (TemplateInfo info: all){
            if (Objects.equals(info.getId(),id)) return info;
        }
        return null;
    }
    private boolean write(List<TemplateInfo> list){
        String string = JSONUtil.toJsonStr(list);
        FileWriter fileWriter = new FileWriter(getPath() + fileName);
        fileWriter.write(string);
        return true;
    }

    private List<TemplateInfo> read(){
        FileReader fileReader = new FileReader(getPath() + fileName);
        String string = fileReader.readString();
        if (!JSONUtil.isJsonArray(string)){
            log.error("读取数据库字段类型对应信息错误");
            return new ArrayList<>();
        }
        return JSONUtil.toList(string,TemplateInfo.class);
    }
}
