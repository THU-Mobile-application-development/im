package com.tsinghua.course.Biz.Controller.Params.ContactParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

/**
 * @描述 查找陌生人的入参
 **/
@BizType(BizTypeEnum.CONTACT_FIND)
public class ContactFindInParams extends CommonInParams {
    // 陌生人用户名
    @Required
    private String contact_username;

    public String getContactUsername() { return contact_username; }
    public void setContactUsername(String contact_username) { this.contact_username = contact_username; }
}



