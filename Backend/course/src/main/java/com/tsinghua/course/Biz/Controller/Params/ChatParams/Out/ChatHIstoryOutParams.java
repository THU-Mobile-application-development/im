package com.tsinghua.course.Biz.Controller.Params.ChatParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Model.ChatProps;
import com.tsinghua.course.Base.Model.ContactProps;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;


@BizType(BizTypeEnum.CHAT_HISTORY)
public class ChatHIstoryOutParams extends CommonOutParams {

    // 所有好友
    ChatProps[] chat;

    public ChatProps[] getChatList() {
        return chat;
    }

    public void setChatList(ChatProps[] chat) {
        this.chat = chat;
    }
}
