package com.tsinghua.course.Base.Model;


import org.springframework.data.mongodb.core.mapping.Document;

@Document("Story")
public class Story {

    String id;

    // 发布者头像
    String avatar;
    // 发布者用户名
    String username;
    // 发布者昵称
    String nickname;
    // 发布时间
    String publishTime;
    // 动态类型
    int type;
    // 图片数组
    String[] images;
    // 视频
    String video;
    // 点赞数
    int likesNum;
    // 点赞用户数组
    String[] likeuser;
    // 评论数
    int commentsNum;
    // 评论用户数组
    StoryReply[] replys;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }

    public String[] getLikeUsername() {
        return likeuser;
    }

    public void setLikeUsername(String[] likeuser) {
        this.likeuser = likeuser;
    }


    public StoryReply[] getReply() {
        return replys;
    }

    public void setReply(StoryReply[] replys) {
        this.replys = replys;
    }


}
