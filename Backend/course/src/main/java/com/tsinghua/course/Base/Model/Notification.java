package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document

public class Notification {

//    // 发送方用户名
//    String send_username;
//    // 接收方用户名
//    String receive_username;
    // 创建时间
    boolean user_read;

    //boolean receive_user_read;

    int send_user_unread_num;

   // int receive_user_unread_num;

//    public String getSendUsername() { return send_username; }
//    public void setSendUsername(String from_username) { this.send_username = send_username; }
//
//    public String getReceiveUsername() { return receive_username; }
//    public void setReceiveUsername(String to_username) { this.receive_username = receive_username; }


    public boolean getUserRead() { return user_read; }
    public void setUserRead(boolean user_read) { this.user_read = user_read; }
//
//    public boolean getReceiveUserRead() { return receive_user_read; }
//    public void setReceiveUserRead(boolean receive_user_read) { this.receive_user_read = receive_user_read; }

    public int getUnreadNum() { return send_user_unread_num; }
    public void setUnreadNum(int send_user_unread_num) { this.send_user_unread_num = send_user_unread_num; }

//    public int getReceiveUserUneadNum() { return receive_user_unread_num; }
//    public void setReceiveUserUneadNum(int receive_user_unread_num) { this.receive_user_unread_num = receive_user_unread_num; }

}
