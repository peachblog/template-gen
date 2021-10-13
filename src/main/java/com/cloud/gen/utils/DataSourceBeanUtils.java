package com.cloud.gen.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.cloud.gen.bean.*;
import com.cloud.gen.enums.ComposeEnums;
import lombok.var;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 信息装配
 * */
public class DataSourceBeanUtils {

    private static List<CustomInfo> customInfoList = null;

    public static void init(TableBean tableBean, List<CustomInfo> customInfos){
        if(Objects.nonNull(customInfoList)){
            customInfoList.clear();
        }else {
            customInfoList = new ArrayList<>();
        }
        // 先装配基本的表字段信息
        systemInfoBean(tableBean);
        // 先替换自定义内容里面基本的表字段信息
        customInfos.stream().forEach(customInfo1->{
            customInfoList.stream().forEach(customInfo -> {
                customInfo1.setValue(customInfo1.getValue().replace(customInfo.getReFiledName(),customInfo.getValue()));
                customInfo1.setReFiledName(ComposeEnums.CUSTOM.before + customInfo1.getReFiledNameName() + ComposeEnums.CUSTOM.after);
            });
        });
        customInfoBean(customInfos,tableBean.getTableFiledBeans());
    }

    /**
     * 基础系统信息装配
     * */
    private static void systemInfoBean(TableBean tableBean){
        SystemInfo systemInfo = new SystemInfo();
        List<CustomInfo> system = systemInfo.getSystem();
        system.stream().forEach(customInfo -> {
            customInfo.setReFiledName(ComposeEnums.SYSTEM.before + customInfo.getReFiledNameName() + ComposeEnums.SYSTEM.after);
            switch (customInfo.getReFiledNameName()){
                case "tableName":customInfo.setValue(StrUtil.upperFirst(StrUtil.toCamelCase(tableBean.getTableName())));break;
                case "mTableName":customInfo.setValue(StrUtil.toCamelCase(tableBean.getTableName()));break;
                case "_tableName":customInfo.setValue(tableBean.getTableName());break;
                case "tableComment":customInfo.setValue(tableBean.getTableComment());break;
                case "date":customInfo.setValue(DateUtil.today());break;
                case "time":customInfo.setValue(DateUtil.format(new Date(), "HH:mm:ss"));break;
            }
            if (!Objects.equals(customInfo.getReFiledNameName(),"pack") &&
                    !Objects.equals(customInfo.getReFiledNameName(),"beanPack")){
                customInfoList.add(customInfo);
            }
        });
    }

    /**
     * 自定义信息装配
     * */
    private static void customInfoBean(List<CustomInfo> customInfos, List<TableFiledBean> tableFiledBeans){
        // 用来装配替换掉customInfos的内容
        List<CustomInfo> customInfos2 = ObjectUtil.cloneByStream(customInfos);
        customInfos.stream().forEach(customInfo -> {
            // 装当前装配信息的包
            List<String> packages = new ArrayList<>();
            if (StrUtil.isEmpty(customInfo.getPackageName()))
                customInfo.setPackageName("");
            // 优先将循环的内容给全部解决
            if(customInfo.getType()==1){
                String string = forCustomInfoBean(customInfo.getValue(), customInfo.getCommon(), customInfo.getCount(), tableFiledBeans,packages);
                customInfo.setValue(string);
            }
            // 当前自定义内容替换
            String s = customInfoBean(customInfo, customInfos2, tableFiledBeans, new ArrayList<String>(){{add(customInfo.getReFiledName());}},packages);
            packages.stream().forEach(s1 -> {
                if (StrUtil.isNotEmpty(s1) && !customInfo.getPackageName().contains(s1)){
                    customInfo.setPackageName(customInfo.getPackageName() + "\n" + s1);
                }
            });
            customInfo.setValue(s);
            customInfoList.add(customInfo);
        });
    }

    /**
     * 装配对应的customInfo数据
     * @param customInfo 需要装配的Bean数据
     * @param customInfos 所有的自定义节点数据
     * @param tableFiledBeans 字段数据
     * @param reNames 已经循环过的节点，防止死循环
     * @param packages 包
     * */
    private static String customInfoBean(CustomInfo customInfo,List<CustomInfo> customInfos,List<TableFiledBean> tableFiledBeans,List<String> reNames,List<String> packages){
        String[] strings = {customInfo.getValue()};
        customInfos.stream().forEach(customInfo1 -> {
            // 判断是否造成死循环，死循环则跳出
            if (strings[0].contains(customInfo1.getReFiledName()) && !reNames.contains(customInfo1.getReFiledName())){
                List<String> newReNames = ObjectUtil.cloneByStream(reNames);
                newReNames.add(customInfo1.getReFiledName());
                // 基础字段直接替换
                if(customInfo1.getType()==0){
                    packages.add(customInfo1.getPackageName());
                    strings[0] = strings[0].replace(customInfo1.getReFiledName(),customInfoBean(customInfo1,customInfos,tableFiledBeans,newReNames,packages));
                }else if(customInfo1.getType()==1){
                    packages.add(customInfo1.getPackageName());
                    // 循环内容替换，和customInfo无关，customInfo如果本身是循环则需要在上一个方法解决
                    String string = forCustomInfoBean(customInfo1.getValue(), customInfo1.getCommon(), customInfo1.getCount(), tableFiledBeans,packages);
                    CustomInfo customInfo2 = BeanUtil.toBean(customInfo1, CustomInfo.class);
                    customInfo2.setValue(string);
                    strings[0] = strings[0].replace(customInfo1.getReFiledName(),customInfoBean(customInfo2,customInfos,tableFiledBeans,newReNames,packages));
                }else if(customInfo1.getType()==2){
                    // 此处是判断，根据指定条件判断是否加载，不加载则直接替换为""
                    tableFiledBeans.stream().forEach(tableFiledBean -> {
                        Boolean falg = (customInfo1.getPtType()==0 && Objects.equals(tableFiledBean.get_columnName(),customInfo1.getPtValue()))
                                || (customInfo1.getPtType()==1 && Objects.equals(tableFiledBean.getDataType(),customInfo1.getPtValue()))
                                || (customInfo1.getPtType()==2 && Objects.equals(tableFiledBean.getColumnKey(),customInfo1.getPtValue())
                                || (customInfo1.getPtType()==3&& Objects.equals(tableFiledBean.getCustomFiled(),customInfo1.getPtValue()))
                                || (customInfo1.getPtType()==5&& Objects.equals(tableFiledBean.getQueryFiled(),customInfo1.getPtValue()))
                                || (customInfo1.getPtType()==4&& tableFiledBean.getAsNull()));
                        int i = strings[0].indexOf(customInfo1.getReFiledName());
                        String common = customInfo1.getCommon();
                        List<String> strings1 = Arrays.asList(common.split(","));
                        if(!strings1.contains(tableFiledBean.get_columnName()) && i!=-1){
                            String str = "";
                            if (falg){
                                packages.add(customInfo1.getPackageName());
                                str = customInfoBean(customInfo1,customInfos,tableFiledBeans,newReNames,packages);
                            }
                            strings[0] = strings[0].substring(0,i) + FiledRe(str,tableFiledBean,packages) + strings[0].substring(i + customInfo1.getReFiledName().length());
                        }
                    });
                }
            }
        });
        return strings[0];
    }

    /**
     * 循环里面系统内容信息替换
     * @param content 循环的内容
     * @param common 不需要循环的字段
     * @param count 字段截取数
     * */
    private static String forCustomInfoBean(String content,String common,Integer count,List<TableFiledBean> tableFiledBeans,List<String> packages){
        List<String> array = new ArrayList<>();
        if(StrUtil.isNotEmpty(common)){
            array.addAll(Arrays.asList(common.split(",")));
        }
        String[] strings = {""};
        tableFiledBeans.stream().forEach(tableFiledBean -> {
            if(!array.contains(tableFiledBean.get_columnName())){
                strings[0] = strings[0] + FiledRe(content,tableFiledBean,packages);
            }
        });
        if (count!=0){
            strings[0] = count>0?strings[0].substring(count-1):strings[0].substring(0,strings[0].length()+count);
        }
        return strings[0];
    }

    private static String FiledRe(String content,TableFiledBean tableFiledBean,List<String> packages){
        SystemInfo systemInfo = new SystemInfo();
        List<CustomInfo> system = systemInfo.getSystemFor();
        system.stream().forEach(customInfo1 -> {
            customInfo1.setReFiledName(ComposeEnums.SYSTEM.before + customInfo1.getReFiledNameName() + ComposeEnums.SYSTEM.after);
        });
        String[] strings1 = {content};
        system.stream().forEach(customInfo1 -> {
            switch (customInfo1.getReFiledNameName()){
                case "javaFiled":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getColumnName());
                    break;
                case "mJavaFiled":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getMColumnName());
                    break;
                case "nickname":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getNickName());
                    break;
                case "sqlFiled":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.get_columnName());
                    break;
                case "javaType":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getJDataType());
                    packages.add(tableFiledBean.getJPackage());
                    break;
                case "Type":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getDataType());
                    break;
                case "comment":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getColumnComment());
                    break;
                case "defaultV":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getColumnDefault());
                    break;
                case "maxLength":
                    strings1[0] = strings1[0].replace(customInfo1.getReFiledName(),tableFiledBean.getMaxLength().toString());
                    break;
            }
        });
        return strings1[0];
    }

    /**
     * 根据模板生成对应文件到对应路径并填充对应数据
     * @param templateInfos 模板文件
     * */
    public static boolean templateGenerate(List<TemplateInfo> templateInfos){
        /**将模板内容引入*/
        templateInfos.stream().forEach(templateInfo -> {
            templateInfo.setReFileName(ComposeEnums.TEMPLATE.before + templateInfo.getReFileNameName() + ComposeEnums.TEMPLATE.after);
            CustomInfo build = CustomInfo.builder().remark(templateInfo.getRemark())
                    .reFiledName(templateInfo.getReFileName())
                    .value(templateInfo.getFileName())
                    .type(0)
                    .packageName("import " + templateInfo.getPackageName() + "." + templateInfo.getFileName()+";").build();
            /**替换内容信息及包的系统信息*/
            customInfoList.stream().forEach(customInfo -> {
                templateInfo.setPath(templateInfo.getPath().replace(customInfo.getReFiledName(),customInfo.getValue()));
                templateInfo.setPackageName(templateInfo.getPackageName().replace(customInfo.getReFiledName(),customInfo.getValue()));
                build.setValue(build.getValue().replace(customInfo.getReFiledName(),customInfo.getValue()));
                build.setPackageName(build.getPackageName().replace(customInfo.getReFiledName(),customInfo.getValue()));
            });
            customInfoList.add(build);
        });
        templateInfos.stream().forEach(templateInfo -> {
            List<String> packets = new ArrayList<>();
            // 替换所有自定义及系统字段
            customInfoList.stream().forEach(customInfo -> {
                if (templateInfo.getContent().contains(customInfo.getReFiledName())){
                    templateInfo.setContent(templateInfo.getContent().replace(customInfo.getReFiledName(),customInfo.getValue()));
                    if (StrUtil.isNotEmpty(customInfo.getPackageName()) && !packets.contains(customInfo.getPackageName())){
                        packets.add(customInfo.getPackageName());
                    }
                }
                templateInfo.setFileName(templateInfo.getFileName().replace(customInfo.getReFiledName(),customInfo.getValue()));
            });
            if (templateInfo.getContent().contains("#{{pack}}#")){
                if (packets.size()>0){
                    StringBuilder sb = new StringBuilder();
                    packets.stream().distinct().collect(Collectors.toList()).forEach(s -> sb.append(s+"\n"));
                    templateInfo.setContent(templateInfo.getContent().replace("#{{pack}}#",sb.toString()));
                }else{
                    templateInfo.setContent(templateInfo.getContent().replace("#{{pack}}#",""));
                }
            }
            templateInfo.setContent(templateInfo.getContent().replace("#{{beanPack}}#",templateInfo.getPackageName()));
            String fileName =  templateInfo.getFileName() + "." + templateInfo.getSuffix();
            String path = templateInfo.getPath() + "/" + fileName;
            if(templateInfo.getAsDel() && FileUtil.exist(path)){
                FileUtil.del(FileUtil.file(path));
                StaticLog.info(path + "已删除");
            }else if (templateInfo.getAsPath() || !FileUtil.exist(path)){
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(templateInfo.getContent());
                StaticLog.info(path + "已生成");
            }
        });
        return true;
    }
}
