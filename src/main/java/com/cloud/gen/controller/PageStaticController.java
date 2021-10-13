package com.cloud.gen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageStaticController {

    @RequestMapping("/")
    public String generateInfo(){
        return "generateInfo";
    }

    @RequestMapping("/template")
    public String templateInfo(){
        return "templateInfo";
    }

    @RequestMapping("/sqlType")
    public String sqlType(){
        return "sqlTypeInfo";
    }

    @RequestMapping("/custom")
    public String custom(){
        return "customInfo";
    }


    @RequestMapping("/database")
    public String database(){
        return "databaseInfo";
    }
}
