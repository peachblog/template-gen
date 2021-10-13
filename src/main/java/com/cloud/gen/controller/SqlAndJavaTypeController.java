package com.cloud.gen.controller;
import com.cloud.gen.bean.DataBaseInfo;
import com.cloud.gen.bean.SqlAndJavaTypeInfo;
import com.cloud.gen.service.SqlAndJavaTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sqlType")
public class SqlAndJavaTypeController {

    @Resource
    private SqlAndJavaTypeService sqlAndJavaTypeService;

    @GetMapping("all")
    public List<SqlAndJavaTypeInfo> listAll(){
        return sqlAndJavaTypeService.getAll();
    }

    @PostMapping("save")
    public Boolean save(@RequestBody SqlAndJavaTypeInfo sqlAndJavaTypeInfo){
        return sqlAndJavaTypeService.save(sqlAndJavaTypeInfo);
    }

    @PostMapping("update")
    public Boolean update(@RequestBody SqlAndJavaTypeInfo sqlAndJavaTypeInfo){
        return sqlAndJavaTypeService.update(sqlAndJavaTypeInfo);
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody DataBaseInfo dataBaseInfo){
        return sqlAndJavaTypeService.delete(dataBaseInfo.getId());
    }
}
