# 书籍：《MyBatis从入门到精通》，作者：刘增辉，示例代码

### 项目环境

1、JDK版本：1.8.0_202

2、Gradle版本：8.0

### 项目构建注意事项

1、编辑build.gradle文件并添加以下代码，否则编译会报编码错误。

```groovy
tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}
```

2、必须特别注意包名配置正确，如果不能确定，建议使用示例包名。

3、目前log4j的1.2.17版本可以正常使用，其他版本还在探索中。