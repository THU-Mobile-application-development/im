package com.tsinghua.course.Base.Model;

import com.tsinghua.course.Base.Enum.UserType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @描述 对应mongodb中的User集合，mongodb是非关系型数据库，可以存储的对象类型很丰富，使用起来方便很多
 **/
@Document("Contact")
public class Contact {
    /**
     * 子对象文档
     */
    // 用户名
    String username;
    // 密码
    String contact_username;
    // 昵称
    String nickname;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContactUsername() {
        return contact_username;
    }

    public void setContactUsername(String contact_username) {
        this.contact_username = contact_username;
    }


}
