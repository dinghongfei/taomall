<html>
<head>
    <title>${hello}</title>
</head>
<body>
学生信息：<br>
学号：${student.id}<br>
姓名：${student.name}<br>
年龄：${student.age}<br>
住址：${student.address}<br>

学生列表：<br>
<table border="1">
    <tr>
        <th>序号</th>
        <th>学号</th>
        <th>姓名</th>
        <th>年龄</th>
        <th>住址</th>
    </tr>
        <#list list as student>
            <#if student_index%2==0>
        <tr bgcolor="red">
            <#else>
        <tr bgcolor="green">
            </#if>
            <td>${student_index}</td>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.age}</td>
            <td>${student.address}</td>
        </tr>
        </#list>
</table>

<br>
日期：${date?string('yyyy/MM/dd HH:mm:ss')}<br>


null值的处理：${val!'我是默认值'}<br>

使用if判断null值：
    <#if val??>
    val not null
    <#else >
    val is null
    </#if>
<br>

include标签测试:
    <#include "hello.ftl">



</body>

</html>
