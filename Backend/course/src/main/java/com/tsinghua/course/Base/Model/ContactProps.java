package com.tsinghua.course.Base.Model;

import com.tsinghua.course.Base.Enum.UserType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ContactProps {
    String contact_username;
    // 好友头像
    String contact_avatar;
    // 好友昵称
    String contact_nickname;

    public String getPropsUsername() {
        return contact_username;
    }

    public void setPropsUsername(String contact_username) {
        this.contact_username = contact_username;
    }

    public String getPropsAvatar() {
        return contact_avatar;
    }

    public void setPropsAvatar(String contact_avatar) {
        this.contact_avatar = contact_avatar;
    }

    public String getPropsNickname() {
        return contact_nickname;
    }

    public void setPropsNickname(String contact_nickname) {
        this.contact_nickname = contact_nickname;
    }


}