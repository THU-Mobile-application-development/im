package com.tsinghua.course.Base.Error;

/**
 * @描述 用户操作警告枚举
 **/
public enum UserWarnEnum implements ExceptionInterface {
    LOGIN_FAILED("UserWarn001", "用户或密码不正确"),

    NEED_LOGIN("UserWarn002", "用户未登录或登录已过期"),

    PERMISSION_DENIED("UserWarn003", "无权限访问对应内容"),

    NO_USERNAME("UserWarn004", "用户名是空的"),

    PASSWORD_INCORRECT("UserWarn005", "密码格式不正确"),
    NO_OLD_PASSWORD("UserWarn006", "没有输入改变之前的密码"),
    NO_NEW_PASSWORD("UserWarn007", "没有输入要改变的密码"),
    NO_CONFIRM_PASSWORD("UserWarn008", "没有输入确认密码"),
    NO_MATCH_CONFIRM_PASSWORD("UserWarn009", "改变的密码与确认密码不同"),
    NO_NEW_USERNAME("UserWarn010", "没有要变的用户名"),
    NO_NEW_NICKNAME("UserWarn011", "没有要变的昵称"),
    NO_NEW_PHONENUMBER("UserWarn012", "没有要变的手机号码"),
    NO_TARGET_USER("UserWarn013", "没有搜索的用户"),



    ;
//    여러가지 경고들을 작성
    UserWarnEnum(String code, String msg) {
        errorCode = code;
        errorMsg = msg;
    }

    private String errorCode;
    private String errorMsg;
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMsg;
    }
}
