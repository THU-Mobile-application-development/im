package com.tsinghua.course.Biz.Controller.Params.StoryParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.STORY_LIKE)

public class StoryLikeInParams extends CommonInParams {
    // 动态id
    @Required
    private String stroyId;
    // 发布动态的用户名
    @Required
    private String publishUsername;

    public String getStroyId() {
        return stroyId;
    }

    public void setStroyId(String stroyId) {
        this.stroyId = stroyId;
    }

    public String getPublishUsername() {
        return publishUsername;
    }

    public void setPublishUsername(String publishUsername) {
        this.publishUsername = publishUsername;
    }


}
