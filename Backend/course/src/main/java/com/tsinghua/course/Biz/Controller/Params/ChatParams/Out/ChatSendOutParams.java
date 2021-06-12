package com.tsinghua.course.Biz.Controller.Params.ChatParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Model.ChatProps;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

import java.util.Date;


@BizType(BizTypeEnum.CHAT_SEND)
public class ChatSendOutParams extends CommonOutParams {

    ChatProps chat;


    public ChatProps getChat() {
        return chat;
    }

    public void setChat(ChatProps chat) {
        this.chat = chat;
    }
//
//    public String getToUsername() { return to_username; }
//    public void setToUsername(String to_username) { this.to_username = to_username; }
//
//    public Date getSendTime() { return send_time; }
//    public void setSendTime(Date send_time) { this.send_time = send_time; }
//
//
//    public String getChatContent() { return content; }
//    public void setChatContent(String content) { this.content = content; }
//
//    public int getChatType(){return type;}
//    public void setChatContent(int type) {this.type = type;}
//


}
