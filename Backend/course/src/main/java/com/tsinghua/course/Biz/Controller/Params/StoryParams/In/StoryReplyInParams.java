package com.tsinghua.course.Biz.Controller.Params.StoryParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

@BizType(BizTypeEnum.STORY_REPLY)
public class StoryReplyInParams extends CommonInParams {
    // 动态id
    @Required
    private String storyId;
    // 评论内容
    @Required
    private String content;
    // 发布动态的用户名
//    @Required
//    private String publishUsername;


    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public String getPublishUsername() {
//        return publishUsername;
//    }
//
//    public void setPublishUsername(String publishUsername) {
//        this.publishUsername = publishUsername;
//    }
}
