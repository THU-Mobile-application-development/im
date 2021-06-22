package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.NeedLogin;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.*;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.ChatParams.Out.ChatListOutParams;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.ContactParams.Out.ContactListOutParams;
import com.tsinghua.course.Biz.Controller.Params.StoryParams.In.StoryLikeInParams;
import com.tsinghua.course.Biz.Controller.Params.StoryParams.In.StoryPublishInParams;
import com.tsinghua.course.Biz.Controller.Params.StoryParams.In.StoryReplyInParams;
import com.tsinghua.course.Biz.Controller.Params.StoryParams.Out.StoryListOutParams;
import com.tsinghua.course.Biz.Processor.StoryProcessor;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import com.tsinghua.course.Biz.Processor.ContactProcessor;

import com.tsinghua.course.Frame.Util.SocketUtil;
import com.tsinghua.course.Frame.Util.ThreadUtil;
import com.tsinghua.course.Frame.Util.UploadFileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component

public class StoryController {
    @Autowired
    UserProcessor userProcessor;
    @Autowired
    ContactProcessor contactProcessor;
    @Autowired
    StoryProcessor storyProcessor;


    @BizType(BizTypeEnum.STORY_PUBLISH)
    @NeedLogin
    public CommonOutParams storyPublish(StoryPublishInParams inParams) throws Exception {
        String username = inParams.getUsername();
        User user = userProcessor.getUserByUsername(username);

        String type = inParams.getType();
        String uploadPath = "/home/uploads/story";
        String content;

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
        //String publishusername = inParams.getPublishUsername();
        storyProcessor.likestorynum(stotyId,username);
        storyProcessor.likestoryuser(stotyId,username);


        return new CommonOutParams(true);
    }


    @BizType(BizTypeEnum.STORY_REPLY)
    @NeedLogin
    public CommonOutParams storyReply(StoryReplyInParams inParams) throws Exception {
        String username = inParams.getUsername();
        String storyId = inParams.getStoryId();
        String content = inParams.getContent();

        StoryReply reply = new StoryReply();
        reply.setContent(content);
        reply.setUsername(username);
        reply.setReplytime(TimeFormat.getTime());


        /* 新建Comment对象 */
        storyProcessor.addReply(storyId, reply);
        storyProcessor.replystorynum(storyId);


        return new CommonOutParams(true);
    }




    @BizType(BizTypeEnum.STORY_LIST)
    @NeedLogin
    public StoryListOutParams getStoryList(CommonInParams inParams) throws Exception {

        String my_username = inParams.getUsername();


        List<Contact> contactList =contactProcessor.getAllContact(my_username);

        List<Story> user_story = new ArrayList<>();
        List<Story> new_story = storyProcessor.getStoryByUsername(my_username);
        user_story.addAll(new_story);

        for(Contact contact : contactList){
            System.out.println(contact.getContactUsername());
            new_story = storyProcessor.getStoryByUsername(contact.getContactUsername());
            user_story.addAll(new_story);
        }

        compare(user_story);



        StoryListOutParams outParams = new StoryListOutParams();
        outParams.setStoryList(user_story);


        return outParams;


    }

    public void compare(List<Story> list) {
        System.out.println("되는겨 마는겨");
        list.sort((o1, o2) -> {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date time1 = null;
        Date time2 = null;
        try {
            time1 = format.parse(o1.getPublishTime());
            time2 = format.parse(o2.getPublishTime());

        }catch( ParseException e){
            e.printStackTrace();
        }
        assert time1 != null;
        if (time1.after(time2))
            return 1;
        else
            return -1;
        });
    }






}
