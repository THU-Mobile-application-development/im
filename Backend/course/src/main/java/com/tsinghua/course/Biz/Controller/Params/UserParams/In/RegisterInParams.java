package com.tsinghua.course.Biz.Controller.Params.UserParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
/**
 * @描述 用户注册的入参
 **/
@BizType(BizTypeEnum.USER_REGISTER)
public class RegisterInParams extends CommonInParams {

    @Required
    private String username;


    // 密码
    @Required
    private String password;
    // 昵称
    @Required
    private String nickname;
    // 手机号
    @Required
    private String phonenumber;


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }


    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
}
