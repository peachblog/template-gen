package com.cloud.gen.bean;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Data
public class TableFiledBean implements Serializable {
    /**别名*/
    private String nickName;
    /**字段名*/
    private String columnName;
    /**首字母大写字段名*/
    private String mColumnName;
    /**下划线字段名*/
    private String _columnName;
    /**默认值*/
    private String columnDefault;
    /**是否必填*/
    private Boolean asNull;
    /**字段类型*/
    private String dataType;
    /**java字段类型*/
    private String jDataType;
    /**java类型需要引入包*/
    private String jPackage;
    /**字段长度*/
    private Integer maxLength;
    /**键值信息*/
    private String columnKey;
    /**备注信息*/
    private String columnComment;
    /**自定义字段*/
    private String customFiled;
    /**查询字段*/
    private String queryFiled;

    public void setColumnName(String columnName) {
        this.columnName = StrUtil.toCamelCase(columnName);
        this.mColumnName = StrUtil.upperFirst(this.columnName);
        this._columnName = StrUtil.toUnderlineCase(columnName);
        System.out.println(this._columnName);
    }
    public void setType(Map<String, SqlAndJavaTypeInfo> sqlAndJavaTypeMap){
        SqlAndJavaTypeInfo orDefault = sqlAndJavaTypeMap.getOrDefault(this.dataType, new SqlAndJavaTypeInfo());
        this.jDataType = StrUtil.isNotEmpty(orDefault.getJavaType())?orDefault.getJavaType():"";
        this.jPackage = StrUtil.isNotEmpty(orDefault.getPack())?orDefault.getPack():"";
    }

    public void setAsNull(Boolean asNull) {
        this.asNull = Objects.isNull(asNull)?false:asNull;
    }

    public void setDataType(String dataType) {
        this.dataType = StrUtil.isNotEmpty(dataType)?dataType:"";
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = Objects.isNull(maxLength)?25:maxLength;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = StrUtil.isNotEmpty(columnKey)?columnKey:"";
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = StrUtil.isNotEmpty(columnComment)?columnComment:"";
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = StrUtil.isNotEmpty(columnDefault)?columnDefault:"";
    }
}
