package com.tsinghua.course.Biz.Controller.Params.ContactParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;


/**
 * @描述 查找新联系人的出参
 **/
@BizType(BizTypeEnum.CONTACT_FIND)
public class ContactFindOutParams extends CommonOutParams {
    // 用户名
    private String contact_username;
    // 昵称
    private String contact_nickname;
    // 头像
    private String contact_avatar;

    private boolean already_contact;


    public boolean getContactAlready() {
        return already_contact;
    }

    public void setContactAlready(boolean already_contact) {
        this.already_contact = already_contact;
    }


    public String getContactUsername() {
        return contact_username;
    }

    public void setContactUsername(String contact_username) {
        this.contact_username = contact_username;
    }

    public String getContactNickname() {
        return contact_nickname;
    }

    public void setContactNickname(String contact_nickname) {
        this.contact_nickname = contact_nickname;
    }

    public String getContactAvatar() {
        return contact_avatar;
    }

    public void setContactAvatar(String contact_avatar) {
        this.contact_avatar = contact_avatar;
    }
}
