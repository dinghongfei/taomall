package com.taomall.item.web.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class TestFreemarker {

    @Test
    public void testFreemarker() throws Exception{
        //1.创建模板文件
        //2.创建一个configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3.设置模板所在的路径
        configuration.setDirectoryForTemplateLoading(new File("E:\\IDEA\\taomall\\taomall-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4.设置模板的字符集，utf-8
        configuration.setDefaultEncoding("UTF-8");
        //5.使用configuration对象加载一个模板文件，需要指定文件名
        Template template = configuration.getTemplate("student.ftl");
        //6.创建一个数据集，pojo或者map，建议map
        Map data = new HashMap();
        data.put("hello", "hello freemarker");
        Student student = new Student(111,"zhangsan",18,"yuelanbeijie");
        data.put("student", student);
        List<Student> list = new ArrayList<>();
        list.add(new Student(111, "zhangsan1", 181, "yuelanbeijie"));
        list.add(new Student(222, "zhangsan2", 182, "yuelanbeijie"));
        list.add(new Student(333, "zhangsan3", 183, "yuelanbeijie"));
        list.add(new Student(444, "zhangsan4", 184, "yuelanbeijie"));
        list.add(new Student(555, "zhangsan5", 185, "yuelanbeijie"));
        list.add(new Student(666, "zhangsan6", 186, "yuelanbeijie"));
        list.add(new Student(777, "zhangsan7", 187, "yuelanbeijie"));
        list.add(new Student(888, "zhangsan8", 188, "yuelanbeijie"));
        list.add(new Student(999, "zhangsan9", 189, "yuelanbeijie"));
        list.add(new Student(000, "zhangsan0", 180, "yuelanbeijie"));
        data.put("list", list);
        data.put("date", new Date());

        //7.创建一个writer对象，指定输出文件的路径及文件名
        Writer out = new FileWriter(new File("E:\\IDEA\\taomall\\taomall-item-web\\src\\main\\webapp\\WEB-INF\\ftl\\student.txt"));
        //8.使用模板对象的process方法输出文件
        template.process(data,out);
        //9.关闭流
        out.close();
    }

}
