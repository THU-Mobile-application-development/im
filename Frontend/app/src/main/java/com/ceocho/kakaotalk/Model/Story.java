package com.ceocho.kakaotalk.Model;

import java.util.List;

public class Story {

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
    int commentsNum;
    // 评论用户数组
    List<Reply> replys;

    public Story( String storyId, String avatar,  String username,String publishTime,String type,String content,int likesNum,List<String> likeuser,List<Reply> replys) {
        this.storyId = storyId;
        this.username = username;
        this.avatar = avatar;
        this.publishTime = publishTime;
        this.type = type;
        this.content = content;
        this.likesNum = likesNum;
        this.likeuser = likeuser;
        this.replys = replys;

    }

    public Story() {

    }




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


    public List<Reply> getReply() {
        return replys;
    }

    public void setReply(List<Reply> replys) {
        this.replys = replys;
    }



}
