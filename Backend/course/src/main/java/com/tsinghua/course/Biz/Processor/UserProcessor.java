package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Enum.UserType;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.UserParams.Out.MyInfoOutParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import com.tsinghua.course.Base.Constant.GlobalConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tsinghua.course.Base.Constant.GlobalConstant.DEFAULT_AVATAR;

/**
 * @描述 用户原子处理器，所有与用户相关的原子操作都在此处理器中执行
 **/
@Component
public class UserProcessor {
    @Autowired
    MongoTemplate mongoTemplate;

    /** 根据用户名从数据库中获取用户 */
    public User getUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        return mongoTemplate.findOne(query, User.class);
    }


    public void createUser(String username, String password, String nickname, String phonenumber) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setPhonenumber(phonenumber);
        user.setUserType(UserType.NORMAL);
//아바타를 디폴트로 설정
        String uploadPath;
        String OSName = System.getProperty("os.name");
        if(OSName.toLowerCase().startsWith("win"))
            uploadPath = "";
        else
            uploadPath = "";

       user.setAvatar(uploadPath + DEFAULT_AVATAR);


        mongoTemplate.insert(user);
    }
    public void ModifyUserPassword(String username, String new_pwd) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        Update update = new Update();
        update.set(KeyConstant.PASSWORD, new_pwd);
        mongoTemplate.upsert(query, update, User.class);
    }


    public void EditUserInfo(String username, String nickname, String phonenumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));

        Update update = new Update();
        update.set(KeyConstant.NICKNAME, nickname);
        update.set(KeyConstant.PHONENUMBER, phonenumber);
        update.set(KeyConstant.USERNAME, username);
        mongoTemplate.upsert(query, update, User.class);


    }

    public void uploadAvatar(String username, String avatar) {
        System.out.println("이게 아바타 패스야");
        System.out.println(avatar);
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        Update update = new Update();
        update.set(KeyConstant.AVATAR, avatar);
        mongoTemplate.upsert(query, update, User.class);
    }


}

