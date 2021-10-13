package com.cloud.gen.controller;

import com.cloud.gen.bean.CustomInfo;
import com.cloud.gen.bean.SystemInfo;
import com.cloud.gen.enums.ComposeEnums;
import com.cloud.gen.service.CustomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/custom")
public class CustomController {


    @GetMapping("system/{type}")
    public List<CustomInfo> getSystemAll(@PathVariable Integer type){
        SystemInfo systemInfo = new SystemInfo();
        List<CustomInfo> system = new ArrayList<>();
        if (type==0){
            system.addAll(systemInfo.getSystem());
        }
        if (type==1){
            system.addAll(systemInfo.getSystemFor());
        }
        system.stream().forEach(customInfo -> customInfo.setReFiledName(ComposeEnums.SYSTEM.before + customInfo.getReFiledNameName() + ComposeEnums.SYSTEM.after));
        return system;
    }

    @Resource
    private CustomService customService;

    @GetMapping("all")
    public List<CustomInfo> getAll(){
        List<CustomInfo> all = customService.getAll();
        all.stream().forEach(customInfo -> customInfo.setReFiledName(ComposeEnums.CUSTOM.before + customInfo.getReFiledNameName() + ComposeEnums.CUSTOM.after));
        return all;
    }

    @PostMapping("save")
    public Boolean save(@RequestBody CustomInfo customInfo){
        return customService.save(customInfo);
    }

    @PostMapping("update")
    public Boolean update(@RequestBody CustomInfo customInfo){
        return customService.update(customInfo);
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody CustomInfo customInfo){
        return customService.delete(customInfo.getId());
    }
}
