package com.tsinghua.course.Biz.Controller.Params.ContactParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;




/**
 * @描述 审核好友申请的入参
 **/
@BizType(BizTypeEnum.CONTACT_ADD)
public class ContactAddInParams extends CommonInParams {
    // 对方用户名
    @Required
    private String add_contact;

    public String getAddUsername() { return add_contact; }
    public void setAddUsername(String add_contact) { this.add_contact = add_contact; }

}
