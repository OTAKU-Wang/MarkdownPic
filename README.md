# 获取token的流程
- 先到[eureka](http://118.190.145.142:18010/)上查看现在注册的服务
    - 用户名:choice
    - 密码：123456
- 查看[api-gateway](http://118.190.145.142:18010/info)服务的地址
- 调用获取token的服务接口
```
curl -XPOST 'http://118.190.145.142:18080/oauth2-service/oauth2/getToken' -d 'appid=10010000&secret=e66d2d3cd0534de82e158be6550b1e35'
```

参数 | 类型 |是否必填 |	最大长度|描述|示例值
---|---|---|---|---|---
appid | string| 是| 8(固定)|appid的值| 10010000
secret | string| 是| 32(固定)| secret的值| e66d2d3cd0534de82e158be6550b1e35


> 其中的`appid`和`secret`为新建商户过程中自动生成.oauth2-cglib为在eureka中注册的oauth2的服务名称.

- 成功的结果如下
```
{"status":200,"msg":"SUCCESS","data":{"access_token":"baf160c1253945e982c739360012292d"}}
```
- 如果`appid`或者`secret`无效的结果如下
```
{"code":"403","msg":"/oauth2?state=9be8211c-7166-4664-a394-400bc5b8c10c&redirect_uri=/oauth2/getToken"}
```
- Token失效
```
{"code":"2000103","msg":"应用授权令牌已过期"}
```
