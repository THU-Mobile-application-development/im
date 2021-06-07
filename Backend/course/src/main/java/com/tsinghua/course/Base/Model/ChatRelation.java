package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document
public class ChatRelation {

    // 发送方用户名
    String my_username;
    // 接收方用户名
    String to_username;
    // 创建时间
    Date create_time;

    List<ChatProps> chat;

    Notification noty;

    public String getMyUsername() { return my_username; }
    public void setMyUsername(String my_username) { this.my_username = my_username; }

    public String getToUsername() { return to_username; }
    public void setToUsername(String to_username) { this.to_username = to_username; }

    public Date getCreateTime() { return create_time; }
    public void setCreateTime(Date create_time) { this.create_time = create_time; }


    public Notification getNotification() { return noty; }
    public void setNotification(Notification noty) { this.noty = noty; }


    public List<ChatProps> getChatList() { return chat; }
    public void setChatList(List<ChatProps> chat) { this.chat = chat; }

}

