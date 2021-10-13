package com.cloud.gen.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.cloud.gen.bean.SqlAndJavaTypeInfo;
import com.cloud.gen.service.SqlAndJavaTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SqlAndJavaTypeServiceImpl extends BaseServiceImpl implements SqlAndJavaTypeService {

    private static final Log log = LogFactory.get();

    private String fileName = "sqlAndJavaType";

    private List<SqlAndJavaTypeInfo> list;
    @Override
    public Boolean save(SqlAndJavaTypeInfo info) {
        List<SqlAndJavaTypeInfo> all = getAll();
        List<SqlAndJavaTypeInfo> collect = all.stream().filter(sqlAndJavaTypeInfo -> sqlAndJavaTypeInfo.getSqlType().equals(info.getSqlType())).collect(Collectors.toList());
        if(collect.size()>0){
            return false;
        }
        info.setId(IdUtil.fastUUID());
        all.add(info);
        return write(all);
    }

    @Override
    public Boolean update(SqlAndJavaTypeInfo info) {
        List<SqlAndJavaTypeInfo> all = getAll();
        all.stream().forEach(info1 -> {
            if (Objects.equals(info1.getId(),info.getId())){
                BeanUtils.copyProperties(info,info1);
            }
        });
        return write(all);
    }

    @Override
    public Boolean delete(String id) {
        List<SqlAndJavaTypeInfo> all = getAll();
        list = all.stream().filter(info -> !Objects.equals(info.getId(), id)).collect(Collectors.toList());
        return  write(list);
    }

    @Override
    public List<SqlAndJavaTypeInfo> getAll() {
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
    public SqlAndJavaTypeInfo getById(String id) {
        List<SqlAndJavaTypeInfo> all = getAll();
        for (SqlAndJavaTypeInfo info: all){
            if (Objects.equals(info.getId(),id)) return info;
        }
        return null;
    }

    private boolean write(List<SqlAndJavaTypeInfo> list){
        String string = JSONUtil.toJsonStr(list);
        FileWriter fileWriter = new FileWriter(getPath() + fileName);
        fileWriter.write(string);
        return true;
    }

    private List<SqlAndJavaTypeInfo> read(){
        FileReader fileReader = new FileReader(getPath() + fileName);
        String string = fileReader.readString();
        if (!JSONUtil.isJsonArray(string)){
            log.error("读取数据库字段类型对应信息错误");
            return new ArrayList<>();
        }
        return JSONUtil.toList(string,SqlAndJavaTypeInfo.class);
    }
}
