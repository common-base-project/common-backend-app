package com.sipue.code.generator.util;

import com.sipue.code.generator.config.GlobalConfig;
import com.sipue.code.generator.config.PackageConfig;
import com.sipue.code.generator.config.model.Column;
import com.sipue.code.generator.config.model.Table;
import com.sipue.code.generator.config.rules.DbColumnToJava;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenUtils {

    private static String currentTableName;

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Mapper.java.vm");
        templates.add("template/DTO.java.vm");
        templates.add("template/VO.java.vm");
        templates.add("template/Mapper.xml.vm");
        /*;
        templates.add("template/menu.sql.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/Dao.java.vm");*/

        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("DTO.java.vm")) {
            return packagePath + "pojo" + File.separator + "dto" + File.separator + packageName + File.separator + className + "PageDTO.java";
        }

        if (template.contains("VO.java.vm")) {
            return packagePath + "pojo" + File.separator + "vo" + File.separator + packageName + File.separator + className + "PageVO.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }
        return null;
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String[] tablePrefixArray) {
        if (null != tablePrefixArray && tablePrefixArray.length > 0) {
            for (String tablePrefix : tablePrefixArray) {
                if (tableName.startsWith(tablePrefix)){
                    tableName = tableName.replaceFirst(tablePrefix, "");
                }
            }
        }
        return columnToJava(tableName);
    }

    /**
     * 生成代码
     */
    public static void generatorCode(GlobalConfig globalConfig, PackageConfig packageConfig, Table table, ZipOutputStream zip) {
        List<Column> columns = table.getColumns();
        boolean hasBigDecimal = false;
        boolean hasDateTime = false;
        boolean hasDate = false;

        //表名转换成Java类名
        String[] tablePrefixArray = {"t_"};
        String className = tableToJava(table.getTableName(), tablePrefixArray);
        table.setClassName(className);
        table.setPackageName(StringUtils.uncapitalize(className));
        //处理列
        for(Column column : columns){
            //列名转换成Java属性名
            String attrName = columnToJava(column.getColumnName());
            column.setAttrName(attrName);
            column.setColumnJavaName(StringUtils.uncapitalize(attrName));
            //列类型转为java类型
            String attrType = DbColumnToJava.coverJavaType(column.getDataType());
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            if (!hasDateTime && attrType.equals("LocalDateTime")) {
                hasDateTime = true;
            }
            if (!hasDate && attrType.equals("LocalDate")) {
                hasDate = true;
            }
            column.setAttrType(attrType);
        }

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", table.getTableName());
        map.put("comments", table.getComments());
        map.put("pk", table.getPk());
        map.put("className", table.getClassName());
        map.put("tablePackage", table.getPackageName());
        //接口要I开头
        map.put("iclassName", "I"+table.getClassName());
        map.put("pathName", table.getClassName().toLowerCase());
        map.put("columns", table.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("hasDate", hasDate);
        map.put("package", packageConfig.getParent());
        map.put("moduleName", packageConfig.getModuleName());
        map.put("author", globalConfig.getAuthor());
        map.put("hasSwagger", globalConfig.getSwagger());
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));

        VelocityContext context = new VelocityContext(map);
        //获取模板列表
        List<String> templates = getTemplates();
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                String agileClass = className;
                //添加到zip
                if(template.equals("template/Service.java.vm")){
                    agileClass = "I" + className;
                }
                zip.putNextEntry(new ZipEntry(getFileName(template, agileClass, packageConfig.getParent(), packageConfig.getModuleName())));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
