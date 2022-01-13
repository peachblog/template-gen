package com.cloud.gen.bean;

import cn.hutool.core.util.StrUtil;
import com.cloud.gen.enums.ComposeEnums;
import lombok.Data;

import java.io.Serializable;

@Data
public class TemplateInfo implements Serializable {

    private String id;
    /**文件名*/
    private String fileName;
    /**替换文件名*/
    private String reFileName;
    /**替换文件名对外信息*/
    private String reFileNameName;
    /**文件后缀*/
    private String suffix;
    /**自定义名称*/
    private String name;
    /**替换内容*/
    private String content;
    /**需要导入的包*/
    private String packageName;
    /**生成存放路径*/
    private String path;
    /**是否删除已生成的*/
    private Boolean asDel = false;
    /**是否覆盖已生成的*/
    private Boolean asPath = false;
    /**是否生成*/
    private Boolean asGen = true;
    /**备注*/
    private String remark;
    public void setPackageName(String packageName) {
        this.packageName = StrUtil.isNotEmpty(packageName)?packageName:"";
    }

    public String getReFileNameNamePack(){
        return ComposeEnums.TEMPLATE.before + this.reFileNameName + "Pack" + ComposeEnums.TEMPLATE.after;
    }
}
