package com.tsinghua.course.Biz.Controller.Params.StoryParams.Out;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Model.ContactProps;
import com.tsinghua.course.Base.Model.Story;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

import java.util.List;


@BizType(BizTypeEnum.STORY_LIST)
public class StoryListOutParams extends CommonOutParams {

    // 所有好友
    List<Story> storyList;


    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

}
