### 项目背景

- 个人经常需要在客户环境操作各种各样的数据库，而客户那边的话工具很少，有时候连个navicat都没有，而且不允许装，免安装版本的也不允许执行(提示禁止)。
- 客户使用的数据库千奇百怪，比如sybase等等，很多都没有听过。
- 所以想的是写一个连接支持JDBC的数据源的通用的工具。

### 功能支持

- 支持任意类型的支持`JDBC数据源`的测试连接。
- 支持任意类型的支持`JDBC数据源`的任意sql语句执行。

- 支持`windows`和`linux`平台。

### 目录说明

```
├── config                         # 配置文件：jdbc连接的driver，url，username，password
│   ├── application.yml
│   ├── application-mysql.yml
│   ├── application-oracle.yml
│   ├── application-sqlserver.yml
│   ├── application-db2.yml
│   ├── application-sybase.yml
├── lib                             # 驱动：jdbc连接所需要的驱动
│   ├── jconn2.jar
│   ├── mysql-connector-java-8.0.21.jar
│   ├── ojdbc8-19.3.0.0.jar
JDBC_TOOL_GOD_VERSION.jar
```

### 使用说明

1. 前往release页面下载`JDBC_TOOL.zip `

2. 修改`config`文件夹下`数据库的相关信息`。

3. `lib`目录下传入自己需要的`驱动jar`包。

4. 启动jar包

```
java -Dloader.path=lib -jar 
```

    