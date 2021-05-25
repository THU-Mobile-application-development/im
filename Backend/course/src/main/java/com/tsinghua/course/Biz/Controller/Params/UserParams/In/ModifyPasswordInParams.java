package com.tsinghua.course.Biz.Controller.Params.UserParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
/**
 * @描述 用户修改密码的入参
 **/
@BizType(BizTypeEnum.USER_MODIFY_PASSWORD)
public class ModifyPasswordInParams extends CommonInParams{
    @Required
    private String old_password;

    @Required
    private String new_password;

    @Required
    private String confirm_password;

    public String getOldPassword() { return old_password; }
   // public void setOldPassword(String old_pwd) { this.old_pwd = old_pwd; }

    public String getNewPassword() { return new_password; }
   // public void setNewPassword(String new_pwd) { this.new_pwd = new_pwd; }

    public String getConfirmPassword() { return confirm_password; }
    //public void setConfirmPassword(String confirm_pwd) { this.confirm_pwd = confirm_pwd; }


}