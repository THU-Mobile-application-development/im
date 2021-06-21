package com.tsinghua.course.Biz.Controller.Params.StoryParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.STORY_LIKE)

public class StoryLikeInParams extends CommonInParams {
    // 动态id
    @Required
    private String storyId;
    // 发布动态的用户名
//    @Required
//    private String publishUsername;

    public String getStroyId() {
        return storyId;
    }

    public void setStroyId(String storyId) {
        this.storyId = storyId;
    }

//    public String getPublishUsername() {
//        return publishUsername;
//    }
//
//    public void setPublishUsername(String publishUsername) {
//        this.publishUsername = publishUsername;
//    }


}
