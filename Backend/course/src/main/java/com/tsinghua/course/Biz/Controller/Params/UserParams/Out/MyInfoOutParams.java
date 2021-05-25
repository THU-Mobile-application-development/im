package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;


/**
 * @描述 我的信息的出参
 **/
@BizType(BizTypeEnum.USER_MYINFO)
public class MyInfoOutParams extends CommonOutParams {
    // 头像图片路径
    private String avatar;
    // 昵称
    private String nickname;
    // 用户名
    private String username;
    // 手机号码
    private String phonenumber;

    public MyInfoOutParams() { this.success = true; }
    public MyInfoOutParams(boolean success) { this.success = success; }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }



}
