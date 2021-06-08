package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document
public class ChatListProps {


    String to_username;

    //String to_nickname;

    String last_time;

    String last_chat;

    String to_avatar;

    int unread_num;


    public String getToUsername() {
        return to_username;
    }

    public void setToUsername(String to_username) {
        this.to_username = to_username;
    }

//    public String getToNickname() { return to_nickname; }
//    public void setToNickname(String to_nickname) { this.to_nickname = to_nickname; }


    public String getLastTime() {
        return last_time;
    }

    public void setLastTime(String last_time) {
        this.last_time = last_time;
    }


    public String getLastChat() {
        return last_chat;
    }

    public void setLastChat(String last_chat) {
        this.last_chat = last_chat;
    }

    public String getToAvatar() {
        return to_avatar;
    }

    public void setToAvatar(String to_avatar) {
        this.to_avatar = to_avatar;
    }


    public int getUnreadNum() {
        return unread_num;
    }

    public void setUnreadNum(int unread_num) {
        this.unread_num = unread_num;
    }

}
