package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document
public class ChatProps {

    String id;

    String my_username;
    // 接收方用户名
    String to_username;
    // 创建时间
    Date send_time;

    String content;
    //文字，图片，视频，音频，位置等等
    int type;


    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getMyUsernmae() { return my_username; }
    public void setMyUsernmae(String my_username) { this.my_username = my_username; }

    public String getToUsername() { return to_username; }
    public void setToUsername(String to_username) { this.to_username = to_username; }

    public Date getSendTime() { return send_time; }
    public void setSendTime(Date send_time) { this.send_time = send_time; }


    public String getChatContent() { return content; }
    public void setChatContent(String content) { this.content = content; }

    public int getChatType(){return type;}
    public void setChatType(int type) {this.type = type;}


}
