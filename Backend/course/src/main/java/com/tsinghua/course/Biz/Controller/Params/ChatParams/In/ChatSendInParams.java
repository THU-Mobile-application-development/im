package com.tsinghua.course.Biz.Controller.Params.ChatParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import io.netty.handler.codec.http.multipart.FileUpload;

import java.util.Date;

@BizType(BizTypeEnum.CHAT_SEND)
public class ChatSendInParams extends CommonInParams {

    @Required
    String from_username;
    // 接收方用户名
    @Required
    String to_username;
    // 创建时间
    Date send_time;

    String content;
    private FileUpload file;


    //文字，图片，视频，音频，位置等等
    @Required
    String type;

    public String getFromUsernmae() {
        return from_username;
    }

    public void setFromUsernmae(String from_username) {
        this.from_username = from_username;
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

    public String getChatType() {
        return type;
    }

    public void setChatType(String type) {
        this.type = type;
    }

    public FileUpload getFile() { return file; }
    public void setFile(FileUpload file) {
        // System.out.println(avatar);
        this.file = file; }

}
