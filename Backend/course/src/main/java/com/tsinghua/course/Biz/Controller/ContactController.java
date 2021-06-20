package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.NeedLogin;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.ChatRelation;
import com.tsinghua.course.Base.Model.Contact;
import com.tsinghua.course.Base.Model.ContactProps;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.ContactParams.In.ContactAddInParams;
import com.tsinghua.course.Biz.Controller.Params.ContactParams.In.ContactDeleteInParams;
import com.tsinghua.course.Biz.Controller.Params.ContactParams.In.ContactFindInParams;
import com.tsinghua.course.Biz.Controller.Params.ContactParams.Out.ContactFindOutParams;
import com.tsinghua.course.Biz.Controller.Params.ContactParams.Out.ContactListOutParams;
import com.tsinghua.course.Biz.Processor.ChatProcessor;
import com.tsinghua.course.Biz.Processor.ContactProcessor;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactController {

    @Autowired
    UserProcessor userProcessor;
    @Autowired
    ContactProcessor contactProcessor;

    @Autowired
    ChatProcessor chatProcessor;

    /**
     * 搜索联系人
     */
    @BizType(BizTypeEnum.CONTACT_FIND)
    @NeedLogin
    public ContactFindOutParams FindContact(ContactFindInParams inParams) throws Exception {


        String contact_username = inParams.getContactUsername();


        User target_user = userProcessor.getUserByUsername(contact_username);

        /* 如果没找到该用户、找到自己，均视为没有找到结果 */
        String username = inParams.getUsername();
        Contact relation = contactProcessor.getRelationByUsername(username, contact_username);

        boolean already_contact;
        if (target_user == null || target_user.getUsername().equals(username)) {
            throw new CourseWarn(UserWarnEnum.NO_TARGET_USER);
        } else if (relation != null) {
            //已经是朋友的话，直接可以进入聊天
            already_contact = true;
        } else {
            already_contact = false;


        }
        String nickname = target_user.getNickname();
        String avatar = target_user.getAvatar();

        //avatar的具体路径
//        int index = avatar.indexOf(RELATIVE_PATH);
//        String avatar_url = "http://" + SERVER_IP + ":" + FILE_PORT + avatar.substring(index);
        ContactFindOutParams outParams = new ContactFindOutParams();
        outParams.setContactAvatar(avatar);
        outParams.setContactUsername(contact_username);
        outParams.setContactNickname(nickname);
        outParams.setContactAlready(already_contact);

        return outParams;
    }

    @BizType(BizTypeEnum.CONTACT_LIST)
    @NeedLogin
    public ContactListOutParams AllContact(CommonInParams inParams) throws Exception {

        String username = inParams.getUsername();//
        List<Contact> contactList = contactProcessor.getAllContact(username);//

        List<ContactProps> contactPropsList = new ArrayList<>();


        for (Contact contact : contactList) {
            String contact_username = contact.getContactUsername();
            User target_user = userProcessor.getUserByUsername(contact_username);

            //String target_avatar = target_user.getAvatar();
//            int index = friend_avatar.indexOf(RELATIVE_PATH);
//            String avatar_url = "http://" + SERVER_IP + ":" + FILE_PORT + friend_avatar.substring(index);
            String target_nickname = target_user.getNickname();
            ContactProps props = new ContactProps();
            props.setPropsUsername(contact_username);
            // props.setPropsAvatar(target_avatar);
            props.setPropsNickname(target_nickname);
            contactPropsList.add(props);
        }

        ContactProps[] result = new ContactProps[contactPropsList.size()];
        contactPropsList.toArray(result);


        ContactListOutParams outParams = new ContactListOutParams();
        outParams.setContactList(result);


        return outParams;
    }


    /**
     * 审核好友申请
     */
    @BizType(BizTypeEnum.CONTACT_ADD)
    @NeedLogin
    public CommonOutParams friendCheckFriendRequest(ContactAddInParams inParams) throws Exception {
        String username = inParams.getUsername();
        String add_username = inParams.getAddUsername();
        contactProcessor.addContact(username, add_username);

        return new CommonOutParams(true);
    }

    /**
     * 删除好友
     */
    @BizType(BizTypeEnum.CONTACT_DELETE)
    @NeedLogin
    public CommonOutParams friendRemoveFriend(ContactDeleteInParams inParams) throws Exception {
        /* 删除好友关系 */
        String username = inParams.getUsername();
        String contact_username = inParams.getDeleteUsername();
        contactProcessor.deleteContact(username, contact_username);
        chatProcessor.deleteRelation(username,contact_username);
//        /* 删除聊天条目（单向）和关系 */
//        ChatUserLink chatUserLink = chatProcessor.getChatUserLink(username, friend_username);
//        String link_id = chatUserLink.getId();
//        chatProcessor.removeChatItem(link_id, username);
//        chatProcessor.removeChatUserLink(link_id);

        return new CommonOutParams(true);
    }

}
