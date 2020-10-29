package com.yx.generator.mybatis;

import com.yx.generator.utils.StringUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @Author jesse
 * @Date 8/31/20 2:59 下午
 **/
public class LombokPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //添加domain的import
        topLevelClass.addImportedType("lombok.*");
        //添加table的import
        topLevelClass.addImportedType("javax.persistence.*");
        //添加swagger的import
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");

        //添加domain的注解
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");

        //添加table绑定
        topLevelClass.addAnnotation("@Table(name = \"" + introspectedTable.getTableConfiguration().getTableName() + "\")");

        // 为每个属性添加注解
        List<Field> fields = topLevelClass.getFields();
        for (Field field : fields) {
            //生成 @Column(name = "name") 注解
            String fieldName = field.getName(); // java字段名是驼峰的，需要转成下划线分割
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }
            String fieldType = field.getType().getShortName();
            //第一个属性若为int或者bigint一般视为自增主键
            if (fields.get(1).equals(field) && (fieldType.equals("Integer") || fieldType.equals("Long"))) {
                field.addAnnotation("@Id");
            }
            //驼峰属性做绑定
            String underlineFieldName = StringUtil.humpToLine(fieldName);
            if (underlineFieldName.contains("_")) {
                field.addAnnotation("@Column(name = \"" + underlineFieldName + "\")");
            }
        }

        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   IntrospectedTable introspectedTable) {
        String basicMapper = getProperties().get("basicMapper").toString();
        String modelPackage = getProperties().get("modelPackage").toString();
        //获取mapper类名
        String[] arrStr= basicMapper.split("\\.");
        String mapperName = arrStr[arrStr.length-1];
        //表名处理为java驼峰
        String modelName = interfaze.getType().getShortName().replace("Mapper","");
        FullyQualifiedJavaType  import_mapper = new FullyQualifiedJavaType(basicMapper);
        FullyQualifiedJavaType import_modle = new FullyQualifiedJavaType(modelPackage +"."+modelName);
        FullyQualifiedJavaType BasicMapper = new FullyQualifiedJavaType(mapperName+"<"+modelName+">");
        //import处理
        interfaze.addImportedType(import_modle);
        interfaze.addImportedType(import_mapper);
        //继承处理
        interfaze.addSuperInterface(BasicMapper);

        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //xml不生成setter
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element,
                                                IntrospectedTable introspectedTable) {
        //xml不生成insert
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        //xml不生成主键查询
        return false;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element,
                                                   IntrospectedTable introspectedTable) {
        //xml不生成查全部
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        //xml不生成delete
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        //xml不生成update
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
        //dao不生成delete
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method,
                                                  Interface interfaze, IntrospectedTable introspectedTable) {
        //dao不生成selectAll
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
        //dao不生成select
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
            Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        //dao不生成update
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
                                               IntrospectedTable introspectedTable) {
        //dao不生成insert
        return false;
    }
}
