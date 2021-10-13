package com.cloud.gen.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomInfo implements Serializable {


    private String id;
    /**替换字段名*/
    private String reFiledName;
    /**替换字段名纯名称*/
    private String reFiledNameName;
    /**替换值*/
    private String value;
    /**分类*/
    private String classification;
    /**需要导入的包*/
    private String packageName;
    /**循环截取字段，正值截取前面负值截取后面*/
    private Integer count;
    /**循环的字段去除哪些，多个用,隔开*/
    private String common;
    /**判断值*/
    private String ptValue;
    /**字段类型：0正常字段1循环字段2判断内容*/
    private Integer type;
    /**判断类型：0判断sql字段名1判断sql字段类型2判断索引3自判断4不为空判断5查询条件判断*/
    private Integer ptType;
    /**备注*/
    private String remark;


    public String getCommon() {
        return Objects.nonNull(common)?common:"";
    }
}
