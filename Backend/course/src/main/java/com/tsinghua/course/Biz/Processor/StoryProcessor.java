package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Model.*;
import com.tsinghua.course.Frame.Util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component

public class StoryProcessor {
    @Autowired
    MongoTemplate mongoTemplate;
    public void publishStory(User user, String type, String content) {
        Story story = new Story();
        List<String> likeuser = new ArrayList<String>();
        List<StoryReply> reply  = new ArrayList<StoryReply>();
        story.setPublishTime(TimeFormat.getTime());
        story.setUsername(user.getUsername());
        story.setNickname(user.getNickname());
        story.setAvatar(user.getAvatar());
        story.setType(type);
        story.setLikesNum(0);
        story.setContent(content);
        story.setLikeUsername(likeuser);
        story.setReply(reply);
        story.setStoryId(CommonUtil.getUniqueId());
        mongoTemplate.insert(story);
    }

    public void likestorynum(String storyId,String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.STORYID).is(storyId));
        Story story = mongoTemplate.findOne(query, Story.class);
        System.out.println(story.getLikesNum());
        //story.setLikesNum(story.getLikesNum()+1);
        Update update = new Update();
        update.set(KeyConstant.LIKESNUM, story.getLikesNum()+1);
        mongoTemplate.upsert(query, update, Story.class);
    }

    public void likestoryuser(String storyId,String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.STORYID).is(storyId));
        Story story = mongoTemplate.findOne(query, Story.class);
        List<String> likeuser = story.getLikeUsername();
        likeuser.add(username);
        Update update = new Update();
        update.set(KeyConstant.LIKEUSER, likeuser);
        mongoTemplate.upsert(query, update, Story.class);

    }
    public void addReply(String storyId,StoryReply reply) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.STORYID).is(storyId));
        Story story = mongoTemplate.findOne(query, Story.class);
        List<StoryReply> replies = story.getReply();
        replies.add(reply);
        Update update = new Update();
        update.set(KeyConstant.REPLY, replies);
        mongoTemplate.upsert(query, update, Story.class);

    }


    public List<Story> getStoryByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        return mongoTemplate.find(query, Story.class);
    }


}
