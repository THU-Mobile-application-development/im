package com.tsinghua.course.Biz.Controller.Params.ContactParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;


/**
 * @描述 删除好友的入参
 **/
@BizType(BizTypeEnum.CONTACT_DELETE)
public class ContactDeleteInParams extends CommonInParams {
    @Required
    private String friend_username;

    public String getContactUsername() { return friend_username; }
    public void setContactUsername(String friend_username) { this.friend_username = friend_username; }
}
