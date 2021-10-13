package com.cloud.gen.enums;

/**缀*/
public enum ComposeEnums {
    SYSTEM("#{{","}}#","系统级的字段替换"),
    CUSTOM("${{","}}#","自定义级的字段替换"),
    TEMPLATE("${{","}}$","模板文件不含后缀名称替换"),
    _FILED("#_{{","}}#","自定义级的字段骆驼峰转下划线替换"),;

    ComposeEnums(String before, String after, String remark) {
        this.before = before;
        this.after = after;
        this.remark = remark;
    }

    public String before;
    public String after;
    public String remark;
}
