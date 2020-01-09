## mongo-client-adapter

一些 mongo client 的扩展

## 基于注解的 MongoDB Document 实体类实现

生成查询 Document 和更新 Document。

### 注解说明

1. @cn.wangz.mongo.adapter.annotation.Document

    声明为 Mongo Document 实体类。

2. @cn.wangz.mongo.adapter.annotation.QueryField

    声明为查询字段，value 为字段名称。

3. @cn.wangz.mongo.adapter.annotation.UpdateField

    声明为更新字段，value 为字段名称，operator 为更新的操作类型（$currentDate, $inc, $min, $max, $mul, $rename, $set, $setOnInsert, $unset）。

### 接口和基类说明

使用时可直接继承 BaseQueryDoc、BaseUpdateDoc、BaseDoc，**注意空值不会处理**

实例参考：[cn/wangz/mongo/adapter/Test.java](/src/test/java/cn/wangz/mongo/adapter/Test.java)

+ cn.wangz.mongo.adapter.query.QueryDoc: 可查询接口
+ cn.wangz.mongo.adapter.update.UpdateDoc: 可更新接口
+ cn.wangz.mongo.adapter.query.BaseQueryDoc: 可查询接口的实现
+ cn.wangz.mongo.adapter.update.BaseUpdateDoc: 可更新接口的实现
+ cn.wangz.mongo.adapter.BaseDoc: 查询接口和更新接口的实现

