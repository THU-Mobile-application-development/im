package com.tsinghua.course.Biz.Controller.Params.UserParams.In;
import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;

import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
/**
 * @描述 用户修改info的入参
 **/
@BizType(BizTypeEnum.USER_EDIT_INFO)

public class EditUsernameInParams extends CommonInParams {
    // 昵称
    @Required
    private String nickname;

    // 手机号码
    @Required
    private String phonenumber;

    // 用户名
    @Required
    private String username;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }


    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String telephone) { this.phonenumber = phonenumber; }




}
