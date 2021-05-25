#Login  
Method: POST   
URL:user/login  
Request:
```
{
    'username': length 5~15（Only English, Number）,
    'password': length 5~15（Only English, Number）
}
```
Correct Response:
```
{
    'msg': Login Success!
}
```
Incorrect Response:
```
{
    'msg': Login failure!
}
```

#Logout（可能需要？没有要求） 

#Register  
Method: POST   
URL:user/register  
Request:
```
{
    'username': length 5~15（Only English, Number）,
    'password': length 5~15（Only English, Number）,
    'nickname': length 5~15（English, Number,Chinese）,
    'email': （Correct format mail）,
    ‘telephone': （11digit number),//邮件或手机选一个
    //其他个人信息（生日，性别？）
}
```
Correct Response:
```
{
    'msg': Register Success!
}
```
Incorrect Response:
```
{
    'msg': Register failure!
}
```

#Get My Info  
Method: GET   
URL:user/myinfo  
Correct Response:
```
{
    'avatar': avatar_path,
    'nickname': my_nickname,
    'username': my_username,
    'telephone': my_telephone,
    'email':my_email,//email,telephone 选一个
    //其他个人信息
}
```
Incorrect Response:  
```
{
    'msg': Can't load my info!
}
```
# Modify Password 

Method: POST  
URL: /user/modifypassword  
Request:  
```
{
    'password': '',
    'new_password': length 5~15（Only English, Number）,
    'confirm_password': same as "new_password"
}
```
Correct Response:  
```
{
    'msg': Modify Password Success!
}
```  
Incorrect Response:
```
{
    'msg': Modify Password failure!
}
```
# Modify My Info 

Method: POST  
URL: /user/modifymyinfo
Request:  
```
{
    'nickname': my_nickname,
    'username': my_username,
    'telephone': my_telephone,
    'email':my_email,//email,telephone 选一个
    //其他个人信息
}
```
Correct Response:  
```
{
    'msg': Modify my info Success!
}
```  
Incorrect Response:  
```
{
    'msg': Modify my info failure!
}
```

# Upload/Modify Avatar

Method: POST  
URL: /user/uploadavatar
Request:
```
{
    'avatar': avatar_path
}
```  
Correct Response:  
```coffeescript
{
    'msg': upload avatar success!
}
```
Error Response:
```coffeescript
{
    'msg': upload avatar failure
}
```
# Get Friends list

Method: GET  
URL: /user/getfriendlist
Correct Response:
```
{
    'friend_username': friend_username,
    'friend_avatar': friend_avatar,
    'friend_nickname': friend_nickname,
}
```
Incorrect Response:
```
{
    'msg': get friends list failure!
}
```

# Search Friend

Method: GET
URL: /user/searchfriend
Request:
```
{
    'content': friend_username
}
```  
Correct Response:
```
{
    'friend_id': friend_username,
    'friend_avatar': friend_avatar,
    'friend_nickname': friend_nickname,
}
```
Incorrect Response:  
```
{
    'msg': Search friend failure!
}
```
# Delete Friend

Method: POST  
URL: /user/deletefriend  
Request:  
```
{
    'friend_username': friend_username
}
```   
Correct Response:   
```
{
    'msg': Delete friend Success!
}
```  
Incorrect Response:   
```
{
    'msg': Delete friend failure!
}
```

# Add Friend

Method: POST  
URL: /user/addfriend  
Request:  
```
{
    'username': friend_username,
}
```  
Correct Response:  
```
{
    'msg': Add friend Success!
}
```  
Error Response:  
```
{
    'msg': Add friend Failure!
}
```

#接受好友申请（没有要求） 
我想直接可以对话，然后收到信息后决定是否加好友或者屏蔽。
