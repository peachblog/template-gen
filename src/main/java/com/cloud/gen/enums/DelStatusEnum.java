package com.cloud.gen.enums;

public enum DelStatusEnum {
    YES(1,"正常"),NO(0,"已删除");

    public int status;

    public String remark;

    DelStatusEnum(int status, String remark) {
        this.status = status;
        this.remark = remark;
    }
}
