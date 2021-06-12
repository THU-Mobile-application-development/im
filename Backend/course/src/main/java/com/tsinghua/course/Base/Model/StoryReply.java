package com.tsinghua.course.Base.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document("StoryReply")
public class StoryReply {

    String username;
    // 评论内容
    String content;
    // 评论时间
    String replytime;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getReplytime() { return replytime; }
    public void setReplytime(String replytime) { this.replytime = replytime; }
}
