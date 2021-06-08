package com.tsinghua.course.Biz.Controller.Params.ChatParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.CHAT_CHECK_RELATION)
public class ChatCheckInParams extends CommonInParams {

    String to_username;

    public String getToUsername() {
        return to_username;
    }

    public void setToUsername(String to_username) {
        this.to_username = to_username;
    }


}
