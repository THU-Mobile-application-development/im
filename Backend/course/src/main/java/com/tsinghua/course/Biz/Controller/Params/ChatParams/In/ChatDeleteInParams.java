package com.tsinghua.course.Biz.Controller.Params.ChatParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

import java.util.Date;

@BizType(BizTypeEnum.CHAT_DELETE)
public class ChatDeleteInParams extends CommonInParams {
    @Required
    String to_username;
    @Required
    String chatId;


    public String getChatId() {
        return chatId;
    }

    public void setchatId(String chatId) {
        this.chatId = chatId;
    }

    public String getToUsername() {
        return to_username;
    }

    public void setToUsername(String to_username) {
        this.to_username = to_username;
    }


}
