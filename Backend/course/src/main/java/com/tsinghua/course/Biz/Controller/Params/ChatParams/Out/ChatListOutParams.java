package com.tsinghua.course.Biz.Controller.Params.ChatParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Model.ChatListProps;
import com.tsinghua.course.Base.Model.ChatProps;
import com.tsinghua.course.Base.Model.ChatRelation;
import com.tsinghua.course.Base.Model.ContactProps;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

@BizType(BizTypeEnum.CHAT_LIST)
public class ChatListOutParams extends CommonOutParams {

    // 所有好友
    ChatListProps[] chat;

    public ChatListProps[] getChatList() {
        return chat;
    }

    public void setChatList(ChatListProps[] chat) {
        this.chat = chat;
    }
}
