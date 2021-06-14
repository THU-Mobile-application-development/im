package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.NeedLogin;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.StoryParams.In.StoryLikeInParams;
import com.tsinghua.course.Biz.Controller.Params.StoryParams.In.StoryPublishInParams;
import com.tsinghua.course.Biz.Processor.StoryProcessor;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import com.tsinghua.course.Frame.Util.SocketUtil;
import com.tsinghua.course.Frame.Util.UploadFileUtils;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Component

public class StoryController {
    @Autowired
    UserProcessor userProcessor;
    @Autowired
    ContactController contactProcessor;
    @Autowired
    StoryProcessor storyProcessor;


    @BizType(BizTypeEnum.STORY_PUBLISH)
    @NeedLogin
    public CommonOutParams storyPublish(StoryPublishInParams inParams) throws Exception {
        String username = inParams.getUsername();
        User user = userProcessor.getUserByUsername(username);

        String type = inParams.getType();
        String uploadPath;
        String content;

        String OSName = System.getProperty("os.name");
        if (OSName.toLowerCase().startsWith("win"))
            uploadPath = "";
        else
            uploadPath = "/usr/local/share/avatar";
        File dir = new File(uploadPath);
        if (!dir.exists())
            dir.mkdirs();
            FileUpload fileUpload = inParams.getContent();

            content = UploadFileUtils.uploadFile(uploadPath,fileUpload.getFilename(),fileUpload);

            storyProcessor.publishStory(user, type, content);

        return new CommonOutParams(true);
    }


    @BizType(BizTypeEnum.STORY_LIKE)
    @NeedLogin
    public CommonOutParams storyLike(StoryLikeInParams inParams) throws Exception {
        String username = inParams.getUsername();
        String stotyId = inParams.getStroyId();
        String publishusername = inParams.getPublishUsername();
        storyProcessor.likestorynum(stotyId,username);
        storyProcessor.likestoryuser(stotyId,username);


        return new CommonOutParams(true);
    }

}
