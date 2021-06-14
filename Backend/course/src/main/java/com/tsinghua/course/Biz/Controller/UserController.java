package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.NeedLogin;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.*;
import com.tsinghua.course.Biz.Controller.Params.UserParams.Out.MyInfoOutParams;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import com.tsinghua.course.Frame.Util.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;


/**
 * @描述 用户控制器，用于执行用户相关的业务
 **/
@Component
public class UserController {

    @Autowired
    UserProcessor userProcessor;

    /**
     * 用户登录业务
     */
    @BizType(BizTypeEnum.USER_LOGIN)
    public CommonOutParams userLogin(LoginInParams inParams) throws Exception {

        String username = inParams.getUsername();
        if (username == null)
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);
        User user = userProcessor.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(inParams.getPassword()))
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);

        /** 登录成功，记录登录状态 */
        ChannelHandlerContext ctx = ThreadUtil.getCtx();
        /** ctx不为空记录WebSocket状态，否则记录http状态 */
        if (ctx != null)
            SocketUtil.setUserSocket(username, ctx);
        else {
            HttpSession httpSession = ThreadUtil.getHttpSession();
            if (httpSession != null) {
                httpSession.setUsername(username);
            }
        }
        return new CommonOutParams(true);
    }

    /**
     * 用户注册业务
     */
    @BizType(BizTypeEnum.USER_REGISTER)
    public CommonOutParams userRegister(RegisterInParams inParams) throws Exception {
        /* 用户名 */
        String username = inParams.getUsername();

        if (username == null)
            throw new CourseWarn(UserWarnEnum.NO_USERNAME);
        //ㅇㅏ이디 정규표현식으로 자르
//        String regex = "^[0-9A-Za-z_]{8,20}$";
//        if (!username.matches(regex))
//            throw new CourseWarn(UserWarnEnum.INVALID_USERNAME);
//        User user = userProcessor.getUserByUsername(username);
//        if (user != null)
//            throw new CourseWarn(UserWarnEnum.DUPLICATE_USERNAME);

        /* 密码 */
        String password = inParams.getPassword();
//        regex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![0-9a-zA-Z]+$)(?![0-9\\W]+$)(?![a-zA-Z\\W]+$)[0-9A-Za-z\\W]{6,18}$";
//        if (!password.matches(regex))
//            throw new CourseWarn(UserWarnEnum.INVALID_PASSWORD);

        /* 昵称 */
        String nickname = inParams.getNickname();

        /* 手机号 */
        String phonenumber = inParams.getPhonenumber();

        /* 创建新用户和默认分组 */
        userProcessor.createUser(username, password, nickname, phonenumber);
        //friendProcessor.addDefaultFriendGroup(username);

        return new CommonOutParams(true);
    }


    /**
     * 用户修改密码业务
     */
    @NeedLogin
    @BizType(BizTypeEnum.USER_MODIFY_PASSWORD)
    public CommonOutParams userModifyPassword(ModifyPasswordInParams inParams) throws Exception {

        String username = inParams.getUsername();
        User user = userProcessor.getUserByUsername(username);
        String old_password = inParams.getOldPassword();
        if (old_password == null)
            throw new CourseWarn(UserWarnEnum.NO_OLD_PASSWORD);
        if (!user.getPassword().equals(old_password))
            throw new CourseWarn(UserWarnEnum.PASSWORD_INCORRECT);
        String new_password = inParams.getNewPassword();
//        regex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![0-9a-zA-Z]+$)(?![0-9\\W]+$)(?![a-zA-Z\\W]+$)[0-9A-Za-z\\W]{6,18}$";
//        if (!password.matches(regex))
//            throw new CourseWarn(UserWarnEnum.INVALID_PASSWORD);
        if (new_password == null)
            throw new CourseWarn(UserWarnEnum.NO_NEW_PASSWORD);
        String confirm_password = inParams.getConfirmPassword();
        if (confirm_password == null)
            throw new CourseWarn(UserWarnEnum.NO_CONFIRM_PASSWORD);
        if (!new_password.equals(confirm_password))
            throw new CourseWarn(UserWarnEnum.NO_MATCH_CONFIRM_PASSWORD);
        userProcessor.ModifyUserPassword(username, new_password);
        return new CommonOutParams(true);
    }

    /**
     * 用户显示info业务
     */
    @NeedLogin
    @BizType(BizTypeEnum.USER_MYINFO)
    public MyInfoOutParams userMyInfo(CommonInParams inParams) throws Exception {

        String username = inParams.getUsername();
        User user = userProcessor.getUserByUsername(username);

        MyInfoOutParams outParams = new MyInfoOutParams(true);
        outParams.setAvatar(user.getAvatar());
        outParams.setUsername(username);
        outParams.setNickname(user.getNickname());
        outParams.setPhonenumber(user.getPhonenumber());

        return outParams;


        //userProcessor.GetMyInfo(username,user.getNickname(),user.getPhonenumber(),user.getAvatar());
        // userProcessor.GetMyInfo(username,user.getNickname(),user.getPhonenumber(),"");

        //return new CommonOutParams(true);
    }

    /**
     * 用户修改info业务
     */
    @NeedLogin
    @BizType(BizTypeEnum.USER_EDIT_INFO)
    public CommonOutParams userEditInfo(EditInfoParams inParams) throws Exception {

        /* 用户名 */
        String username = inParams.getUsername();
        if (username.equals(""))
            throw new CourseWarn(UserWarnEnum.NO_NEW_USERNAME);


        /* 昵称 */
        String nickname = inParams.getNickname();
        if (nickname.equals(""))
            throw new CourseWarn(UserWarnEnum.NO_NEW_NICKNAME);

        String phonenumber = inParams.getPhonenumber();
        if (phonenumber.equals(""))
            throw new CourseWarn(UserWarnEnum.NO_NEW_PHONENUMBER);


        userProcessor.EditUserInfo(username, nickname, phonenumber);
        return new CommonOutParams(true);
    }

    /**
     * 上传头像
     */
    @BizType(BizTypeEnum.USER_AVATAR)
    @NeedLogin
    public CommonOutParams userUploadAvatar(AvatarInParams inParams) throws Exception {
        System.out.println("여기까지는 오는거니?");
        // 根据Windows和Linux配置不同的头像保存路径
        String uploadPath;
        String avatar;
        String OSName = System.getProperty("os.name");
        if (OSName.toLowerCase().startsWith("win"))
            uploadPath = "";
        else
            uploadPath = "/usr/local/share/avatar";
        System.out.println(uploadPath);
        File dir = new File(uploadPath);
        if (!dir.exists())
            dir.mkdirs();
        FileUpload fileUpload = inParams.getAvatar();

        avatar = UploadFileUtils.uploadFile(uploadPath,fileUpload.getFilename(),fileUpload);
        System.out.println(avatar);
        String username = inParams.getUsername();
        userProcessor.uploadAvatar(username, avatar);

        return new CommonOutParams(true);

        // 获取文件内容
        //MultipartFile file = inParams.getAvatar();
        //InputStream inputStream = file.getInputStream();
        // 获取原始文件名
//        String originalName = file.getOriginalFilename();
//        UploadFileUtils upload = new UploadFileUtils();
//        avatar = upload.uploadFile(uploadPath, originalName, file);
//        // 生成uuid名称
        //String username = inParams.getUsername();
        //userProcessor.uploadAvatar(username, avatar);

    }


}
