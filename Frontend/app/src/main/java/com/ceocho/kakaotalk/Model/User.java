package com.ceocho.kakaotalk.Model;

public class User {

    private String username;
    private String avatar;
    private String nickname;
    private String lastchat;
    private String lasttime;
    private String unreadnum;

    public User( String username, String avatar,  String nickname,String lastchat,String lasttime,String unreadnum) {
        this.username = username;
        this.avatar = avatar;
        this.nickname = nickname;
        this.lastchat = lastchat;
        this.lasttime = lasttime;
        this.unreadnum = unreadnum;

    }

    public User() {

    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) { this.avatar = avatar; }



    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getLastchat() { return lastchat; }
    public void setLastchat(String lastchat) { this.lastchat = lastchat; }

    public String getLasttime() { return lasttime; }
    public void setLasttime(String lasttime) { this.lasttime = lasttime; }


    public String getUnreadnum() { return unreadnum; }
    public void setUnreadnum(String unreadnum) { this.unreadnum = unreadnum; }



}



