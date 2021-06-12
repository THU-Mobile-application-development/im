package com.tsinghua.course.Biz.Controller.Params.ContactParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Model.Contact;
import com.tsinghua.course.Base.Model.ContactProps;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;


@BizType(BizTypeEnum.CONTACT_LIST)
public class ContactListOutParams extends CommonOutParams {

    // 所有好友
    ContactProps[] contacts;

    public ContactProps[] getContactList() {
        return contacts;
    }

    public void setContactList(ContactProps[] contacts) {
        this.contacts = contacts;
    }
}
