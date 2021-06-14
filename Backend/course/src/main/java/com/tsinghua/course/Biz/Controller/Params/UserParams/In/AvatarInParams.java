package com.tsinghua.course.Biz.Controller.Params.UserParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.springframework.web.multipart.MultipartFile;
/**
 * @描述 上传头像的入参
 **/
@BizType(BizTypeEnum.USER_AVATAR)

public class AvatarInParams extends CommonInParams{


    // 头像
    @Required
    private FileUpload avatar;

    public FileUpload getAvatar() { return avatar; }
    public void setAvatar(FileUpload avatar) {
       // System.out.println(avatar);
        this.avatar = avatar; }

}


