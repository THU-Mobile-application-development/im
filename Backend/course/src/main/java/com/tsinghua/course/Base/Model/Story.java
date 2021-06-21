package com.tsinghua.course.Base.Model;


import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Document("Story")
public class Story  {

    String storyId;

    // 发布者头像
    String avatar;
    // 发布者用户名
    String username;
    // 发布者昵称
    String nickname;
    // 发布时间
    String publishTime;
    // 动态类型
    String type;
    // 图片数组
    String content;
    // 点赞数
    int likesNum;
    // 点赞用户数组
    List<String> likeuser;
    // 评论数
    int replyNum;
    // 评论用户数组
    List<StoryReply> replys;


    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }

    public List<String> getLikeUsername() {
        return likeuser;
    }

    public void setLikeUsername(List<String> likeuser) {
        this.likeuser = likeuser;
    }


    public List<StoryReply> getReply() {
        return replys;
    }

    public void setReply(List<StoryReply> replys) {
        this.replys = replys;
    }





}
