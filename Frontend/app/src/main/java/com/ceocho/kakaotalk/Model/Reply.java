package com.ceocho.kakaotalk.Model;

import java.util.List;

public class Reply {
    String username;
    // 评论内容
    String content;
    // 评论时间
    String replytime;

    public Reply(String username, String content, String replytime) {
        this.username = username;
        this.content = content;
        this.replytime = replytime;
    }

    public Reply() {

    }


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
