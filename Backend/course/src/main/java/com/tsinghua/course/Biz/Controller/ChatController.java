package com.tsinghua.course.Biz.Controller;


import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.NeedLogin;
import com.tsinghua.course.Base.Model.*;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.In.ChatCheckInParams;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.In.ChatDeleteInParams;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.In.ChatHistoryInParams;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.In.ChatSendInParams;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.Out.ChatCheckOutParams;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.Out.ChatHIstoryOutParams;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.Out.ChatListOutParams;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.Out.ChatSendOutParams;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.ContactParams.Out.ContactListOutParams;
import com.tsinghua.course.Biz.Processor.ChatProcessor;
import com.tsinghua.course.Biz.Processor.ContactProcessor;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import com.tsinghua.course.Frame.Util.SocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tsinghua.course.Base.Constant.GlobalConstant.*;

@Component
public class ChatController {
    @Autowired
    UserProcessor userProcessor;
    @Autowired
    ChatProcessor chatProcessor;


    @BizType(BizTypeEnum.CHAT_LIST)
    @NeedLogin
    public ChatListOutParams getChatList(CommonInParams inParams) throws Exception {
        String my_username = inParams.getUsername();
        List<ChatRelation> chatList = chatProcessor.getChatList(my_username);


//        List<ChatRelation> chatPropsList = new ArrayList<>();
//
//
//
//        for (Contact contact: contactList) {
//            String contact_username = contact.getContactUsername();
//            User target_user = userProcessor.getUserByUsername(contact_username);
//
//            //String target_avatar = target_user.getAvatar();
////            int index = friend_avatar.indexOf(RELATIVE_PATH);
////            String avatar_url = "http://" + SERVER_IP + ":" + FILE_PORT + friend_avatar.substring(index);
//            String target_nickname = target_user.getNickname();
//            System.out.println(target_nickname);
//            ContactProps props= new ContactProps();
//            props.setPropsUsername(contact_username);
//            // props.setPropsAvatar(target_avatar);
//            props.setPropsNickname(target_nickname);
//            contactPropsList.add(props);
//        }

        ChatRelation[] result = new ChatRelation[chatList.size()];
        chatList.toArray(result);


        ChatListOutParams outParams = new ChatListOutParams();
        outParams.setChatList(result);




        return outParams;




    }







    @BizType(BizTypeEnum.CHAT_CHECK_RELATION)
    @NeedLogin
    public ChatCheckOutParams chatGetChatRelation(ChatCheckInParams inParams) throws Exception {
        String my_username = inParams.getUsername();
        String to_username = inParams.getToUsername();

        ChatRelation relation = chatProcessor.getChatRelation(my_username, to_username);
        Notification init_noty = new Notification();
        init_noty.setUserRead(false);
        init_noty.setUnreadNum(0);
        List<ChatProps> chat = new ArrayList<>();


        if (relation == null) {

            chatProcessor.createChatRelation(my_username, to_username,init_noty,chat);
            chatProcessor.createChatRelation(to_username, my_username,init_noty,chat);
        }
        /* 否则，调整聊天条目的在线状态 */
        else {
            init_noty.setUserRead(true);
            init_noty.setUnreadNum(0);
            chatProcessor.updateChatNotification(my_username, to_username,init_noty);
        }

        ChatCheckOutParams outParams = new ChatCheckOutParams();
        outParams.setToUsername(to_username);

        return outParams;
    }



    /** 发送消息给指定用户 */
    @BizType(BizTypeEnum.CHAT_SEND)
    @NeedLogin

    public CommonOutParams chatSendMessage(ChatSendInParams inParams) throws Exception {
        String my_username = inParams.getUsername();
        String to_username = inParams.getToUsername();



        ChatProps chat = new ChatProps();
        Date time = new Date();

        chat.setMyUsernmae(my_username);
        chat.setChatType(inParams.getChatType());
        chat.setToUsername(to_username);
        chat.setSendTime(time);
        chat.setChatContent(inParams.getChatContent());

        ChatProps chat_to = new ChatProps();
        chat_to.setMyUsernmae(to_username);
        chat_to.setChatType(inParams.getChatType());
        chat_to.setToUsername(my_username);
        chat_to.setSendTime(time);
        chat_to.setChatContent(inParams.getChatContent());


        // 插入新消息
        chatProcessor.updateChat(my_username, to_username, chat);
        chatProcessor.updateChat(to_username, my_username, chat_to);

        ChatRelation to_Relation = chatProcessor.getChatRelation(to_username,my_username);
        to_Relation.getNotification().setUnreadNum(to_Relation.getNotification().getUnreadNum()+1);
        to_Relation.getNotification().setUserRead(true);
        chatProcessor.updateChatNotification(to_username, my_username,to_Relation.getNotification());



        ChatSendOutParams outParams = new ChatSendOutParams();
        outParams.setChat(chat);
        SocketUtil.sendMessageToUser(to_username, outParams);

        return new CommonOutParams(true);


    }

//    /** 查看历史记录 */
    @BizType(BizTypeEnum.CHAT_HISTORY)
    @NeedLogin
    public ChatHIstoryOutParams chatGetHistory(ChatHistoryInParams inParams) throws Exception {
        String to_username = inParams.getToUsername();
        String my_username = inParams.getUsername();

//        List<ChatProps> chat_list = chatProcessor.getAllChat(my_username,to_username);
//        System.out.println(chat_list.size());
//        System.out.println(chat_list.get(0).getChatContent());
//        System.out.println(chat_list.get(1).getChatContent());


        ChatRelation chat = chatProcessor.getChatRelation(my_username,to_username);
        List<ChatProps> chat_list = chat.getChatList();
        ChatProps[] result = new ChatProps[chat_list.size()];
        chat_list.toArray(result);


        ChatHIstoryOutParams outParams = new ChatHIstoryOutParams();
        outParams.setChatList(result);

        return outParams;
    }

    @BizType(BizTypeEnum.CHAT_DELETE)
    @NeedLogin
    public CommonOutParams chatDelete(ChatDeleteInParams inParams) throws Exception {
        System.out.println("fuckkkkkkkkk");
        String to_username = inParams.getToUsername();
        String my_username = inParams.getUsername();
        Date send_time = inParams.getTime();
//        ChatRelation chat_relate = chatProcessor.getChatRelation(my_username,to_username);
//        List<ChatProps> chat_list = chat_relate.getChatList();
//        chat_list
        System.out.println("is it working?");
        chatProcessor.deleteChat(my_username,to_username, send_time);
        return new CommonOutParams(true);
    }



}
