package com.cloud.gen.controller;


import com.cloud.gen.bean.DataBaseInfo;
import com.cloud.gen.service.DataBaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1/database")
public class DataBaseController {

    @Resource
    private DataBaseService dataBaseService;

    @GetMapping("all")
    public List<DataBaseInfo> getAll(){
        return dataBaseService.getAll();
    }


    @PostMapping("save")
    public Boolean save(@RequestBody DataBaseInfo dataBaseInfo){
        return dataBaseService.save(dataBaseInfo);
    }

    @PostMapping("update")
    public Boolean update(@RequestBody DataBaseInfo dataBaseInfo){
        return dataBaseService.update(dataBaseInfo);
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody DataBaseInfo dataBaseInfo){
        return dataBaseService.delete(dataBaseInfo.getId());
    }
}
