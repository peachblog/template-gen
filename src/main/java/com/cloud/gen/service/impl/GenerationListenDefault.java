package com.cloud.gen.service.impl;

import cn.hutool.log.StaticLog;
import com.cloud.gen.bean.TableBean;
import com.cloud.gen.bean.TemplateInfo;
import com.cloud.gen.service.GenerationListen;

import java.util.List;
import java.util.Objects;

/**
 * 监听默认抽象
 * */
public abstract class GenerationListenDefault implements GenerationListen {

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
                        StaticLog.info("{}表已生成",tableBean.getTableName());
                    };
                }
            }
        }
        return GenerationListenManage.SINGLE;
    }

    public GenerationListenDefault() {
        configure(new GenerationListenManage());
    }

    @Override
    public void run(TableBean tableBean, List<TemplateInfo> list) {};
    protected abstract void configure(GenerationListenManage generationListen);
}
