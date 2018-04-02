# json数据接口调用请求说明

http请求方式：POST（请使用https协议）：

`https://gateway.choicesaas.cn/${服务名}/${api地址}?appid=APPID&sign=SIGN&timestamp=TIMESTAMP&nonce=NONCE&token=TOKEN`

```
{
"alipay":"",
"cardcode":"20081186032698978598",
"cardid":"",
"coupon":"1",
"groupcode":"1",
"mobtel":"",
"pk_store":"0a60171ceeaf44fa83da",
"vscode":"88888888",
"wechat":""
}
```

header头需要设置为:`application/json;charset=UTF-8`

# 计算签名

- 对参数进行排序将所有的参数（sign除外）按照参数名ASCII码从小到大排序（字典序），并用&连接。
    -	参数名ASCII码从小到大排序（字典排序）
    -	参数名区分大小写
    - json数据需要对json的内容进行排序，如果是json数组也需要对数组内的数据进行排序。
    - 参数中包含中文时，中文保持原文，无需对其单独转码，后台进行统一处理。
    
- 得到加密之前的字符串按照请求url + ? + 排序后的参数 + secret的顺序进行连接，得到加密前的字符串
    - json数据排序的key为biz_content。这个key为后台计算sign时添加的，不需要在发送数据时添加上。
    
- 对加密前的字符串进行MD5加密，得到签名

# 示例

- 发送请求的数据如下：

```
http://gateway.choicesaas.cn/basemember/member/chargeAmount?appid=10450000&nonce=2c1f06da53db408293c5b9609c5a3b5e&timestamp=1522141637&token=697d1ed4aa874e2087e3fe44c4dac2ae&sign=fc9359a0812a2dafe39fb2e8f54963111
```

```
{
　　"amount":"1",
　　"cardcode":"021141342541",
　　"dworkdate":"2018-03-27",
　　"groupcode":"5aa223c34f78a3476dd1390f",
　　"operator":"1",
　　"operatorname":"1",
　　"paytype":"cash",
　　"pk_store":"2f3e4d2ac38b487ba5a9",
　　"posid":"5",
　　"serial":"79baf7eab13409092181302c73bd9bc8",
　　"tradeno":"180315466113",
　　"vscode":"88888888"
}
```
- 计算签名需要的字符串为：

```
http://gateway.choicesaas.cn/basemember/member/chargeAmount?appid=10450000&biz_content={"amount":"1","cardcode":"021141342541","dworkdate":"2018-03-27","groupcode":"5aa223c34f78a3476dd1390f","operator":"1","operatorname":"1","paytype":"cash","pk_store":"2f3e4d2ac38b487ba5a9","posid":"5","serial":"79baf7eab13409092181302c73bd9bc8","tradeno":"180315466113","vscode":"88888888"}&nonce=2c1f06da53db408293c5b9609c5a3b5e&timestamp=1522141637&token=697d1ed4aa874e2087e3fe44c4dac2ae&secret=965a2ce1bdbff5a1f44b63d1b28d22b4

```
> 需要注意的是其中的`biz_content`只用于生成计算的字符串,并不需要放到真正的请求中！

获取计算的sign d9eb350701c1e9a4fbbb0430e6af2da7
