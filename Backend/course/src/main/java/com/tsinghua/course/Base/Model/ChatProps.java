package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class ChatProps {

    String id;

    String from_username;
    // 接收方用户名
    String to_username;
    // 创建时间
    String send_time;

    String content;
    //文字，图片，视频，音频，位置等等
    int type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUsername() {
        return from_username;
    }

    public void setFromUsername(String from_username) {
        this.from_username = from_username;
    }

    public String getToUsername() {
        return to_username;
    }

    public void setToUsername(String to_username) {
        this.to_username = to_username;
    }

    public String getSendTime() {
        return send_time;
    }

    public void setSendTime(String send_time) {
        this.send_time = send_time;
    }


    public String getChatContent() {
        return content;
    }

    public void setChatContent(String content) {
        this.content = content;
    }

    public int getChatType() {
        return type;
    }

    public void setChatType(int type) {
        this.type = type;
    }


}
