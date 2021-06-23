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
import com.tsinghua.course.Frame.Util.CommonUtil;
import com.tsinghua.course.Frame.Util.SocketUtil;
import com.tsinghua.course.Frame.Util.ThreadUtil;
import com.tsinghua.course.Frame.Util.UploadFileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
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
        System.out.println("1111111");
        String from_username = inParams.getUsername();
        List<ChatRelation> chatList = new ArrayList<>();
        System.out.println(chatProcessor.getChatList(from_username).get(0).getCreateTime());
        chatList.addAll(chatProcessor.getChatList(from_username));

        System.out.println("2222222");
        List<ChatListProps> chatPropsList = new ArrayList<>();
        ChannelHandlerContext ctx = ThreadUtil.getCtx();
        for (ChatRelation chat : chatList) {

            String to_username = chat.getToUsername();
            String last_time = chat.getChatList().get(chat.getChatList().size() - 1).getSendTime();
            int unread_num = chat.getNotification().getUnreadNum();
            //String last_time_str = last_time;
            String last_chat;
            if (chat.getChatList().get(chat.getChatList().size() - 1).getChatType().equals("0") ) {
                last_chat = chat.getChatList().get(chat.getChatList().size() - 1).getChatContent();
            } else if (chat.getChatList().get(chat.getChatList().size() - 1).getChatType().equals("1")) {
                last_chat = "image";
            } else if (chat.getChatList().get(chat.getChatList().size() - 1).getChatType().equals("2")) {
                last_chat = "video";

            } else if (chat.getChatList().get(chat.getChatList().size() - 1).getChatType().equals("3")) {
                last_chat = "audio";

            } else {
                last_chat = "geo";
            }
            String to_avatar = userProcessor.getUserByUsername(to_username).getAvatar();

//            int index = friend_avatar.indexOf(RELATIVE_PATH);
//            String avatar_url = "http://" + SERVER_IP + ":" + FILE_PORT + friend_avatar.substring(index);
            ChatListProps props = new ChatListProps();
            props.setLastChat(last_chat);
            props.setToUsername(to_username);
            props.setLastTime(last_time);
            props.setToAvatar(to_avatar);
            props.setUnreadNum(unread_num);
            chatPropsList.add(props);
        }

        System.out.println("333333");


        ChatListProps[] result = new ChatListProps[chatPropsList.size()];
        chatPropsList.toArray(result);


        ChatListOutParams outParams = new ChatListOutParams();
        outParams.setChatList(result);

        System.out.println("444444");

        return outParams;


    }


    @BizType(BizTypeEnum.CHAT_CHECK_RELATION)
    @NeedLogin
    public ChatCheckOutParams chatGetChatRelation(ChatCheckInParams inParams) throws Exception {
        String from_username = inParams.getUsername();
        String to_username = inParams.getToUsername();

        ChatRelation relation = chatProcessor.getChatRelation(from_username, to_username);
        Notification init_noty = new Notification();
        init_noty.setUserRead(false);
        init_noty.setUnreadNum(0);


        Notification init_noty_to = new Notification();
        init_noty_to.setUserRead(true);
        init_noty_to.setUnreadNum(1);
        List<ChatProps> chat = new ArrayList<>();

        String time = TimeFormat.getTime();
        ChatProps chatp = new ChatProps();
        chatp.setToUsername(to_username);
        chatp.setChatContent("lets get talk!");
        chatp.setChatType("0");
        chatp.setSendTime(time);
        chatp.setFromUsername(from_username);
        chatp.setChatId(CommonUtil.getUniqueId());
        chat.add(chatp);


        List<ChatProps> chat_to = new ArrayList<>();

        ChatProps chatp_to = new ChatProps();
        chatp_to.setToUsername(to_username);
        chatp_to.setChatContent("lets get talk!");
        chatp_to.setChatType("0");
        chatp_to.setSendTime(time);
        chatp_to.setFromUsername(from_username);
        chatp_to.setChatId(CommonUtil.getUniqueId());
        chat_to.add(chatp_to);

        if (relation == null) {

            chatProcessor.createChatRelation(from_username, to_username, init_noty, chat);
            chatProcessor.createChatRelation(to_username, from_username, init_noty_to, chat_to);
        }
        /* 否则，调整聊天条目的在线状态 */
        else {
            init_noty.setUserRead(false);
            init_noty.setUnreadNum(0);
            chatProcessor.updateChatNotification(from_username, to_username, init_noty);
        }

        ChatCheckOutParams outParams = new ChatCheckOutParams();
        outParams.setToUsername(to_username);

        return outParams;
    }


    /**
     * 发送消息给指定用户
     */
    @BizType(BizTypeEnum.CHAT_SEND)
    public CommonOutParams chatSendMessage(ChatSendInParams inParams) throws Exception {

        String from_username = inParams.getFromUsernmae();
        System.out.println(from_username);
        String to_username = inParams.getToUsername();

        String time = TimeFormat.getTime();

        ChatProps chat = new ChatProps();
        chat.setChatType(inParams.getChatType());
        if(chat.getChatType().equals("0")||chat.getChatType().equals("4")) {
            chat.setChatContent(inParams.getChatContent());
        }
        else{
            String uploadPath = "/home/uploads/chat";
            String file;
            File dir = new File(uploadPath);
            if (!dir.exists())
                dir.mkdirs();
            FileUpload fileUpload = inParams.getFile();

            file = UploadFileUtils.uploadFile(uploadPath,fileUpload.getFilename(),fileUpload);
            chat.setChatContent(file);
        }
        chat.setFromUsername(from_username);
        chat.setToUsername(to_username);
        chat.setSendTime(time);
        chat.setChatId(CommonUtil.getUniqueId());

        ChatProps chat_to = new ChatProps();
        chat_to.setChatType(inParams.getChatType());
        if(chat_to.getChatType().equals("0")||chat_to.getChatType().equals("4")) {
            chat_to.setChatContent(inParams.getChatContent());
        }
        else{
            String uploadPath = "/home/uploads/chat";
            String file;
            File dir = new File(uploadPath);
            if (!dir.exists())
                dir.mkdirs();
            FileUpload fileUpload = inParams.getFile();

            file = UploadFileUtils.uploadFile(uploadPath,fileUpload.getFilename(),fileUpload);
            chat_to.setChatContent(file);

        }


        chat_to.setFromUsername(from_username);
        chat_to.setToUsername(to_username);
        chat_to.setSendTime(time);
        chat_to.setChatId(CommonUtil.getUniqueId());


        // 插入新消息
        chatProcessor.updateChat(from_username, to_username, chat);
        chatProcessor.updateChat(to_username, from_username, chat_to);

        ChatRelation to_Relation = chatProcessor.getChatRelation(to_username, from_username);
        to_Relation.getNotification().setUnreadNum(to_Relation.getNotification().getUnreadNum() + 1);
        to_Relation.getNotification().setUserRead(true);
        chatProcessor.updateChatNotification(to_username, from_username, to_Relation.getNotification());


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


        ChatRelation chat = chatProcessor.getChatRelation(my_username, to_username);
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
        String to_username = inParams.getToUsername();
        String my_username = inParams.getUsername();
        String chatId = inParams.getChatId();
//        ChatRelation chat_relate = chatProcessor.getChatRelation(my_username,to_username);
//        List<ChatProps> chat_list = chat_relate.getChatList();
//        chat_list
        chatProcessor.deleteChat(my_username, to_username, chatId);
        return new CommonOutParams(true);
    }


}
