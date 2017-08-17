# OAuth2.0接口文档

### 1. /api/oauth2/authorize

##### method: GET/POST 

##### request:
```
{
    client_id:     "client_id",
    redirect_uri:  "redirect_uri"
}
```
##### response:

* success: redirect to {redirect_uri}+"&code={code}"



### 2. /api/oauth2/access_token
##### method: POST 
##### request:
```
{
    client_id:      "client_id",
    client_secret:  "client_secret",
    code:           "code"
}
```
##### response:
```
{
    "result": {
        "access_token": "9BD9EAEA57C64B92A1376474DB77F7D0",
        "uid":          1,
        "expires_in":   3600
    },
    "status": "ok"
}
 
{
    "message": "用户名、密码错误"|"code错误",
    "status":  "error"
}
 
{
    "message": "code过期",
    "status":  "unlogin"
}
```

### 3. /api/oauth2/get_token_info
##### method: POST 
##### request:
```
{
    access_token: "access_token"
}
```
##### response:
```
{
    "result": {
        "uid":        1,
        "client_id":  "client_id",
        "create_at":  1502903394,
        "expires_in": 3595(s)
    },
    "status": "ok"
}
 
{
    "message": "token错误或已过期",
    "status":  "error"
}
```

### 4. /api/oauth2/revokeoauth2
##### method: POST 
##### request:
```
{
    access_token: "access_token"
}
```
##### response:
```
{
    "status": "ok"
}
```

### 5. /api/oauth2/userinfo
##### method: POST 
##### request:
```
{
    access_token: "access_token"
}
```
##### response:
```
{
    "result": {
        "user": {
            "userName":   "userName",
            "createTime": 1502892643000
            ......
        }
    },
    "status": "ok"
}
 
{
    "message": "token错误或已过期",
    "status":  "error"
}
```