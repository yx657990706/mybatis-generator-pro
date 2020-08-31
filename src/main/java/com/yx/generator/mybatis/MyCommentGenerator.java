package com.yx.generator.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;


public class MyCommentGenerator extends DefaultCommentGenerator {
    /**
     * properties配置文件
     */
    private Properties properties;
    /**
     * properties配置文件
     */
    private Properties systemPro;

    /*
     * 父类时间
     */
    private boolean suppressDate;

    /**
     * 父类所有注释
     * <br>是否去除自动生成的注释
     */
    private boolean suppressAllComments;

    /**
     * 当前时间
     */
    private String currentDateStr;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    /**
     * 给Java文件加注释，这个注释是在文件的顶部，也就是package上面。
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        compilationUnit.addFileCommentLine("/*");
//        compilationUnit.addFileCommentLine("*");
//        compilationUnit.addFileCommentLine("* "+compilationUnit.getType().getShortName()+".java");
//        compilationUnit.addFileCommentLine("* Copyright(C) 2019-2024");
//        compilationUnit.addFileCommentLine("* @date "+sdf.format(new Date())+"");
//        compilationUnit.addFileCommentLine("*/");
    }

    /**
     * Java类的类注释
     */
//    @Override
//    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
//        String shortName = innerClass.getType().getShortName();
//        innerClass.addJavaDocLine("/**");
//        innerClass.addJavaDocLine(" * @Title " + shortName);
//        innerClass.addJavaDocLine(" * @Description " + introspectedTable.getFullyQualifiedTable());
//        innerClass.addJavaDocLine(" * @Author "+systemPro.getProperty("user.name"));
//        innerClass.addJavaDocLine(" * @date " + getDateString());
//        innerClass.addJavaDocLine(" */");
//    }

    /**
     * 为类添加注释
     */
//    @Override
//    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
//        String shortName = innerClass.getType().getShortName();
//        innerClass.addJavaDocLine("/**");
//        innerClass.addJavaDocLine(" * @Title " + shortName);
//        innerClass.addJavaDocLine(" * @Description " + introspectedTable.getFullyQualifiedTable());
//        innerClass.addJavaDocLine(" * @author " + systemPro.getProperty("user.name"));
//        innerClass.addJavaDocLine(" * @Author leo");
//        innerClass.addJavaDocLine(" * @date " + currentDateStr);
//        innerClass.addJavaDocLine(" */");
//    }


    /**
     * Mybatis的Mapper.xml文件里面的注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {

    }

    /**
     * @return
     * @throws
     * @Title addConfigurationProperties
     * @Description: 从该配置中的任何属性添加此实例的属性CommentGenerator配置。
     * 这个方法将在任何其他方法之前被调用。
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * @return
     * @throws
     * @Title getDateString
     * @Description: 此方法返回格式化的日期字符串以包含在Javadoc标记中和XML注释。 如果您不想要日期，则可以返回null在这些文档元素中。
     */
    @Override
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }

    /**
     * @param javaElement
     * @param markAsDoNotDelete
     * @throws
     * @Title addJavadocTag
     * @Description: 此方法为其添加了自定义javadoc标签。
     */
    @Override
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * Java属性注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" *" + introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(" */");
    }

    /**
     * 为字段添加注释<br>
     * model对象中字段的注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");
    }

    /**
     * 普通方法的注释，这里主要是XXXMapper.java里面的接口方法的注释
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String method_name = method.getName();
        String returnType = String.valueOf(method.getReturnType());
        method.addJavaDocLine("/**");
        if (method.isConstructor()) {
            method.addJavaDocLine(" * 构造查询条件");
        }
        if ("insert".equals(method_name)) {
            method.addJavaDocLine(" * 自动生成的insert方法");
        } else if ("selectAll".equals(method_name)) {
            method.addJavaDocLine(" * 自动生成的selectAll方法");
        }
        method.addJavaDocLine(" * ");
        //参数处理
        final List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            sb.setLength(0);
            sb.append(" * @param ");
            sb.append(parameter.getName());
            method.addJavaDocLine(sb.toString());
        }
        //返回类型处理
        if(!"void".equals(returnType)){
            method.addJavaDocLine(" * ");
            method.addJavaDocLine(" * @return");
        }

        method.addJavaDocLine(" */");
    }

    /**
     * 为模型类添加注释
     */
    @Override
    public void addModelClassComment(TopLevelClass arg0, IntrospectedTable arg1) {

    }

    /**
     * 为调用此方法作为根元素的第一个子节点添加注释。
     */
    @Override
    public void addRootComment(XmlElement arg0) {

    }
}
