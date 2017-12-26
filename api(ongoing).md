# 状态码

状态码|状态解释
:-:|:-|
200|请求成功
400|请求失败
401|手机号错误
402|密码错误
403|用户名错误

# 接口示例

## 用户注册

- 简要描述

   用户注册接口

- 请求url

   http://97.64.21.155:8001/user/signup

- 请求方式
  
  POST

- 请求参数

参数名|必选|类型|说明
:-|:-:|:-|:-
username|是|string|用户名
password|是|string|密码
phonenum|是|string|手机号
qq|否|string|绑定的qq号
weibo|否|string|绑定的微博号

- 请求示例

```
{
    "username":"yujun",
    "password":"yujun12345",
    "phonenum":"1233241514",
    "qq":"",
    "weibo":""
}
```

- 返回示例

```
{
    "state":200
}
```

- 返回参数

参数|类型|说明
:-|:-|:-|
state|int|状态

- 备注


## 用户登录

- 简要描述
 
  用户登录接口

- 请求url
  
  http://97.64.21.155:8001/user/login

- 请求方式

  POST

- 请求参数

参数名|必选|类型|说明
:-|:-:|:-|:-
password|是|string|密码
phonenum|是|string|手机号

- 请求示例

```
{
  "password":"yujun12345",
  "phonenum":"12334556"
}
```

- 返回示例

```
{
    "data": {
        "phonenum": "12334556",
        "picture": "pictures/12334556.jpg",
        "qq": "",
        "username": "yujun",
        "wechat": ""
    },
    "state": 200
}
```

- 返回参数

参数|类型|说明
:-|:-|:-|
state|int|状态
data|json|用户数据
username|string|用户名
phonenum|string|手机号
qq|string|绑定的qq号
weibo|string|绑定的微博号
picture|string|用户头像地址

- 备注

## 上传头像（未使用）

- 简要描述

   用户上传头像接口

- 请求url

   http://97.64.21.155:8001/user/picture/upload

- 请求方式
  
  POST

- 请求参数

  无

- 请求示例

![](http://images2017.cnblogs.com/blog/1011927/201711/1011927-20171115001518343-1351168463.png)

![](http://images2017.cnblogs.com/blog/1011927/201711/1011927-20171115001553484-141481166.png)

- 返回示例

```
{
    "state":200
}
```

- 返回参数

参数|类型|说明
:-|:-|:-|
state|int|状态

- 备注

传上来的图片要以手机号为名字，且只能上传png、jpeg、jpg、gif格式的图片。

## 下载头像（未使用）

- 简要描述

   用户下载头像接口

- 请求url

   http://97.64.21.155:8001/user/picture/download

- 请求方式
  
  POST

- 请求参数

参数名|必选|类型|说明
:-|:-:|:-|:-
password|是|string|密码
phonenum|是|string|手机号


- 请求示例

```
{
	"password":"yujun12345",
	"phonenum":"12334556"
}
```

- 返回示例
![](http://images2017.cnblogs.com/blog/1011927/201711/1011927-20171115001831874-1720502679.png)

![](http://images2017.cnblogs.com/blog/1011927/201711/1011927-20171115001929015-1355265507.png)

- 返回参数

  无

- 备注

## 修改个人信息

- 简要描述

   用户修改个人信息接口

- 请求url

   http://97.64.21.155:8001/user/change/data

- 请求方式
  
  POST

- 请求参数

参数名|必选|类型|说明
:-|:-:|:-|:-
username|是|string|用户名
password|是|string|密码
phonenum|是|string|手机号
qq|否|string|绑定的qq号
weibo|否|string|绑定的微博号

- 请求示例

```
{
	"username":"jun",
	"password":"35789",
	"phonenum":"987654321",
	"qq":"12345",
	"weibo":""
}
```

- 返回示例

```
{
    "state":200
}
```

- 返回参数

参数|类型|说明
:-|:-|:-|
state|int|状态


- 备注


## 修改密码

- 简要描述

  用户修改密码的接口

- 请求url

   http://97.64.21.155:8001/user/change/password

- 请求方式
  
  POST

- 请求参数

参数名|必选|类型|说明
:-|:-:|:-|:-
oldpassword|是|string|旧密码
newpassword|是|string|新密码
phonenum|是|string|手机号

- 请求示例

```
{
  "oldpassword":"123456",
	"newpassword":"456846142",
	"phonenum":"987654321"
}
```

- 返回示例

```
{
    "state":200
}
```

- 返回参数

参数|类型|说明
:-|:-|:-|
state|int|状态


- 备注

## 重置密码

- 简要描述

  用户忘记密码时重置密码的接口

- 请求url

   http://97.64.21.155:8001/user/reset/password

- 请求方式
  
  POST

- 请求参数

参数名|必选|类型|说明
:-|:-:|:-|:-
username|是|string|用户名
newpassword|是|string|新密码
phonenum|是|string|手机号

- 请求示例

```
{
  "username":"123",
	"newpassword":"123",
	"phonenum":"123"
}
```

- 返回示例

```
{
    "state":200
}
```

- 返回参数

参数|类型|说明
:-|:-|:-|
state|int|状态


- 备注