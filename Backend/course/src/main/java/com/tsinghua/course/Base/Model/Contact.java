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
    /** 子对象文档 */
    public static class SubObj {
        /**
         * 存储的时间
         */
        String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    // mongodb唯一id
    String id;
    // 用户名
    String username;
    // 密码
    String contact_username;
    // 昵称
    String nickname;

    SubObj subObj;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getContactUsername() { return contact_username; }
    public void setContactUsername(String contact_username) { this.contact_username = contact_username; }

    public SubObj getSubObj() {
        return subObj;
    }

    public void setSubObj(SubObj subObj) {
        this.subObj = subObj;
    }


}
