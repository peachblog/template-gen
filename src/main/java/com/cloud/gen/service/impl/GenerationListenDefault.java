package com.cloud.gen.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.StaticLog;
import com.cloud.gen.bean.TableBean;
import com.cloud.gen.bean.TemplateInfo;
import com.cloud.gen.service.GenerationListen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 监听默认抽象
 * */
public abstract class GenerationListenDefault {

    protected static class GenerationListenManage{
        private static GenerationListen SINGLE;
        public static void listen(GenerationListen listen){
            SINGLE = listen;
        }
    }

    public static GenerationListen getSINGLE(){
        if(Objects.isNull(GenerationListenManage.SINGLE)){
            synchronized (GenerationListenDefault.class){
                if(Objects.isNull(GenerationListenManage.SINGLE)){
                    GenerationListenManage.SINGLE = (tableBean,list)->{
                        StaticLog.info(tableBean.getTableName() + "表代码已生成，可继承GenerationListenDefault调用GenerationListenManage类里面的listen方法传递方法");
                    };
                }
            }
        }
        return GenerationListenManage.SINGLE;
    }

    public GenerationListenDefault() {
        configure(new GenerationListenManage());
    }

    protected abstract void configure(GenerationListenManage generationListen);
}
