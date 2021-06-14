package com.tsinghua.course.Biz.Controller.Params.StoryParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import io.netty.handler.codec.http.multipart.FileUpload;

@BizType(BizTypeEnum.STORY_PUBLISH)

public class StoryPublishInParams extends CommonInParams {
    private String type;
    private FileUpload content;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    public FileUpload getContent() {
        return content;
    }
    public void setContent(FileUpload content) {
        this.content = content;
    }


}
