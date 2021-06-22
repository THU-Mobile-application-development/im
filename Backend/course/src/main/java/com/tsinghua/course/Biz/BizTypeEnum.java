package com.tsinghua.course.Biz;

import com.tsinghua.course.Biz.Controller.*;

/**
 * @描述 业务类型枚举，所有的业务类型都需要枚举在此类中
 **/
public enum BizTypeEnum {
    /**
     * 以下为用户业务类型
     */
    USER_LOGIN(UserController.class, "/user/login", "用户登录"),
    USER_REGISTER(UserController.class, "/user/register", "用户注册"),
    USER_MODIFY_PASSWORD(UserController.class, "/user/modify_password", "用户修改密码"),
    USER_MYINFO(UserController.class, "/user/myinfo", "用户显示info"),
    USER_EDIT_INFO(UserController.class, "/user/edit_info", "用户修改个人信息"),
    USER_EDIT_USERNAME(UserController.class, "/user/edit_username", "修改用户名"),

    USER_AVATAR(UserController.class, "/user/avatar", "用户修改头像"),

    /**
     * 以下为联系人业务类型
     */
    CONTACT_FIND(ContactController.class, "/contact/find", "查找用户"),
    CONTACT_ADD(ContactController.class, "/contact/add", "添加用户"),
    CONTACT_DELETE(ContactController.class, "/contact/delete", "删除用户"),
    CONTACT_LIST(ContactController.class, "/contact/list", "用户目录"),


    CHAT_CHECK_RELATION(ChatController.class, "/chat/check_relation", "查看是否已经在聊天"),
    CHAT_SEND(ChatController.class, "/chat/chat_send", "发送聊天消息"),
    CHAT_DELETE(ChatController.class, "/chat/chat_delete", "删除聊天内容"),
    CHAT_HISTORY(ChatController.class, "/chat/chat_history", "查看聊天历史记录"),
    CHAT_LIST(ChatController.class, "/chat/list", "聊天目录"),


    STORY_PUBLISH(StoryController.class, "/story/publish", "发布动态"),
    STORY_REPLY(StoryController.class, "/story/reply", "回复动态"),
    STORY_LIKE(StoryController.class, "/story/like", "点赞动态"),
    STORY_LIST(StoryController.class, "/story/list", "动态目录"),


    /**
     * 定时任务业务测试
     */
    LOG_TEST(TimerController.class, null, "定时日志测试"),

    /**
     * 测试业务，在书写正式代码时可以删除，在书写正式代码前先运行测试业务，如果测试业务无问题说明各模块正常
     */
    LOGIN_TEST(TestController.class, "/test/loginPermission", "登录控制测试"),
    ADMIN_TEST(TestController.class, "/test/adminPermission", "管理员权限控制测试"),
    REDIS_TEST(TestController.class, "/test/redis", "redis缓存测试"),
    TIMER_TEST(TestController.class, "/test/timer", "定时器测试"),
    ERROR_TEST(TestController.class, "/test/error", "内部报错测试"),
    FILE_UPLOAD_TEST(TestController.class, "/test/upload", "文件上传测试"),
    FILE_DOWNLOAD_TEST(TestController.class, "/test/url", "获取文件下载的路径"),
    MULTI_RETURN_TEST(TestController.class, "/test/multiParams", "返回多个参数的测试"),
    MONGODB_TEST(TestController.class, "/test/mongodb", "mongodb数据库功能测试");

    BizTypeEnum(Class<?> controller, String httpPath, String description) {
        this.controller = controller;
        this.description = description;
        this.httpPath = httpPath;
    }

    /**
     * 执行业务具体的类
     */
    Class<?> controller;
    /**
     * 业务对应的http请求路径
     */
    String httpPath;
    /**
     * 业务描述
     */
    String description;

    public Class<?> getControllerClass() {
        return controller;
    }

    public String getDescription() {
        return description;
    }

    public String getHttpPath() {
        return httpPath;
    }
}
