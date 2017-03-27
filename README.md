HR-Shop

HR-Shop是一个基于自营模式的商城app，专注于在校内售卖零食、烟酒等，配送上门，以方便学生足不出户购买零食的意愿，以及解决学校小卖部深夜不开店的烦恼。此为app服务端代码。

-----
### 主要技术

项目管理：Maven  
开发框架：Spring,SpringMVC,Hibernate  
缓存：ehcache做商品首页缓存，redis做短信验证码缓存  
数据验证:Hibernate Validator  
日志工具：Slf4j + Log4j  
数据库：MySQL  
版本控制：git  
短信平台：阿里大于

-----
### 功能
基于Restful风格开发，目前基本已实现了前台需要的功能，包括登录注册，商品展示，购物车，订单，异常处理等模块，后续将开发管理系统进行数据的管理。

-----
## API

_（括号里为参数列表说明）_

* 查询全部商品种类  
GET  /api/categories

* 首页根据页码展示新上架/销量最高商品数据(flag,页号)    
（flag=1表示取出新上架商品，flag=2表示取出销量由高到低商品）  
GET /api/products/flag/{flag}?pageNum=1  

* 分类页根据页码展示该类别下的商品(商品种类cid,页号)  
GET /api/products/category/{cid}?pageNum=1  

* 进入商品详情页传递商品种类数据（商品id）  
GET /api/products/details/{id}

* 搜索页面显示推荐的搜索关键字
GET /api/products/searchList/

* 按关键字查询相关商品
GET /api/products/keyword/{name}?pageNum=1 

* 商品添加到购物车(商品细分种类ptid，数量，用户id,token)  
POST /api/protypes/cart/add?ptid=1&number=1&id=1&token=token  

* 修改购物车商品数量(数量，购物项id)  
PATCH /api/carts/{id}?number=1

* 删除购物车项(购物项id)  
DELETE /api/carts/{id}

* 显示购物车所有商品(用户id,token)  
GET /api/carts/user?id=1&token=token  

* 在购物车页面点击立即结算时请求该地址，判断所选择商品库存是否为0(参数为购物车中所选商品的商品细分种类ptid构成的字符串，格式如"1,2,3,4"  ,用户id,token)  
GET /api/carts/buy?ptids=ptids&numbers=numbers&id=1&token=token

* 下单  (收货人name，地址address，备注remark，电话号码phone，购物车勾选数据对象，商品细分种类对象，对应商品对象，用户id , token)  
期望格式
```javascript
{
  "name": "sysho",
  "address": "清风阁",
  "remark": "加两双筷子",
  "phone": "123124325235",
  "cart": [
    {
      "id": 1,
      "number": 43,
      "protype": {
        "id": 4,
        "name": "老坛酸菜",
        "pic": "4.jpg",
        "inventory": 100,
        "product": {
          "id": 2,
          "name": "方便面",
          "price": 23
        }
      }
    },
    {
      "id": 6,
      "number": 3,
      "protype": {
        "id": 4,
        "name": "烧烤味",
        "pic": "4.jpg",
        "inventory": 50,
        "product": {
          "id": 2,
          "name": "薯片",
          "price": 23
        }
      }
    }
  ]
}
```    
POST /api/forders/user?id=1&order_json=order_json&token=token  

* 查询用户的订单记录(用户id，页号,token)  
GET /api/forders/user?id=1&pageNum=1&token=token

* 删除订单(订单id，用户id,token)
DELETE /api/forders/{fid}?id=1&token=token  

* 取消订单(订单id，用户id,token)  
PATCH /api/forders/{fid}?id=1&token=token  

* 账号密码登录(返回token给客户端)(账号，密码)  
POST /api/users/login?input=input&password=password

* 电话号码登录(电话号码)  
POST /api/users/phoneLogin?phone=phone

* 注册时发送验证码到客户端(电话号码)  
成功返回格式
```javascript 
{
  "alibaba_aliqin_fc_sms_num_send_response": {
    "result": {
      "err_code": "0",
      "model": "106050020644^1108205048156",
      "success": true
    },
    "request_id": "zddu10wa4c4w"
  }
}  
``` 
错误返回格式 
```javascript 
{
  "error_response": {
    "code": 15,
    "msg": "Remote service error",
    "sub_code": "isv.MOBILE_NUMBER_ILLEGAL",
    "sub_msg": "号码格式错误",
    "request_id": "z24ugzp8esng"
  }
}
```  
POST /api/users/registerCode?phone=phone

* 登录时发送验证码到客户端(电话号码)  
  
成功返回格式  
```javascript 
{
  "alibaba_aliqin_fc_sms_num_send_response": {
    "result": {
      "err_code": "0",
      "model": "106050020644^1108205048156",
      "success": true
    },
    "request_id": "zddu10wa4c4w"
  }
}  
``` 
错误返回格式  
```javascript 
{
  "error_response": {
    "code": 15,
    "msg": "Remote service error",
    "sub_code": "isv.MOBILE_NUMBER_ILLEGAL",
    "sub_msg": "号码格式错误",
    "request_id": "z24ugzp8esng"
  }
}
```  
POST /api/users/loginCode?phone=phone

* 判断验证码是否正确（电话号码，验证码）  
GET /api/users/{phone}/checkCode?code=code

* 用户输入正确的验证码后，填入密码进行注册（电话号码，密码）  
POST /api/users/{phone}/register?password=password

* 用户修改用户名(匹配3-5个汉字，或3-10个字节（中文，英文，数字及下划线(_)）)  
PATCH /api/users/{id}/updateUsername?username=username

* 用户修改密码(匹配6-32个字符，可包含中文，英文，数字及下划线(_))  
PATCH /api/users/{id}/updatePassoword?password=password

**统一返回格式：{"status":"HTTP状态码" , "msg":"消息"}** _（有关用户登录注册的操作将返回token，username等数据）_  


### 开发周期
2017.2.5 - 至今

### 负责模块
此项目由3个开发人员参与开发，本人负责app服务器端的编码。
### 总结与收获
通过该项目，不仅学习、巩固了新的知识，也体验到做一个想真正投入使用的项目，需要注意很多细节问题。以及团队成员之间如何进行有效沟通。
