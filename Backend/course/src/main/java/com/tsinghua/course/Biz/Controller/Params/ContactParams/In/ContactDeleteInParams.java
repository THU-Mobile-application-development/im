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
    private String delete_username;

    public String getDeleteUsername() { return delete_username; }
    public void setDeleteUsername(String friend_username) { this.delete_username = delete_username; }
}
