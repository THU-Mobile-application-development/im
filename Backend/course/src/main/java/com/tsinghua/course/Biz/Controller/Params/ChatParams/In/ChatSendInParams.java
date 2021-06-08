package com.tsinghua.course.Biz.Controller.Params.ChatParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

import java.util.Date;

@BizType(BizTypeEnum.CHAT_SEND)
public class ChatSendInParams extends CommonInParams {


    String my_username;
    // 接收方用户名
    @Required
    String to_username;
    // 创建时间
    Date send_time;

    @Required
    String content;
    //文字，图片，视频，音频，位置等等
    @Required
    int type;

    public String getMyUsernmae() {
        return my_username;
    }

    public void setMyUsernmae(String my_username) {
        this.my_username = my_username;
    }

    public String getToUsername() {
        return to_username;
    }

    public void setToUsername(String to_username) {
        this.to_username = to_username;
    }

    public Date getSendTime() {
        return send_time;
    }

    public void setSendTime(Date send_time) {
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

    public void setChatContent(int type) {
        this.type = type;
    }


}
