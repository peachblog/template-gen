package com.cloud.gen.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.cloud.gen.bean.DataBaseInfo;
import com.cloud.gen.service.DataBaseService;
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
public class DataBaseServiceImpl extends BaseServiceImpl implements DataBaseService {
    private static final Log log = LogFactory.get();

    private String fileName = "database";

    private List<DataBaseInfo> list;

    @Override
    public Boolean save(DataBaseInfo baseInfo) {
        List<DataBaseInfo> all = getAll();
        baseInfo.setId(IdUtil.fastUUID());
        all.add(baseInfo);
        return write(all);
    }

    @Override
    public Boolean update(DataBaseInfo baseInfo) {
        List<DataBaseInfo> all = getAll();
        all.stream().forEach(baseInfo1 -> {
            if (Objects.equals(baseInfo1.getId(),baseInfo.getId())){
                BeanUtils.copyProperties(baseInfo,baseInfo1);
            }
        });
        return write(all);
    }

    @Override
    public Boolean delete(String id) {
        List<DataBaseInfo> all = getAll();
        list = all.stream().filter(baseInfo -> !Objects.equals(baseInfo.getId(), id)).collect(Collectors.toList());
        return  write(list);
    }

    @Override
    public List<DataBaseInfo> getAll() {
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
    public DataBaseInfo getById(String id) {
        List<DataBaseInfo> all = getAll();
        for (DataBaseInfo baseInfo: all){
            if (Objects.equals(baseInfo.getId(),id)) return baseInfo;
        }
        return null;
    }

    private boolean write(List<DataBaseInfo> list){
        String string = JSONUtil.toJsonStr(list);
        FileWriter fileWriter = new FileWriter(getPath() + fileName);
        fileWriter.write(string);
        return true;
    }

    private List<DataBaseInfo> read(){
        FileReader fileReader = new FileReader(getPath() + fileName);
        String string = fileReader.readString();
        if (!JSONUtil.isJsonArray(string)){
            log.error("读取数据库信息错误");
            return new ArrayList<>();
        }
        return JSONUtil.toList(string,DataBaseInfo.class);
    }
}
