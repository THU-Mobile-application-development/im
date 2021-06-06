package com.tsinghua.course.Biz.Controller.Params.ChatParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

import java.util.Date;

@BizType(BizTypeEnum.CHAT_DELETE)
public class ChatDeleteInParams extends CommonInParams {
@Required
    String to_username;
@Required
    Date send_time;


    public Date getTime(){return send_time;}
    public void setTime(Date send_time){this.send_time = send_time;}

    public String getToUsername(){return to_username;}
    public void setToUsername(String to_username){this.to_username = to_username;}


}
