package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Model.ChatProps;
import com.tsinghua.course.Base.Model.ChatRelation;
import com.tsinghua.course.Base.Model.Contact;
import com.tsinghua.course.Base.Model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component

public class ChatProcessor {


    @Autowired
    MongoTemplate mongoTemplate;


    public List<ChatRelation> getChatList(String from_username) {
        System.out.println(from_username);
        System.out.println("여기는 되니 ");
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.FROM_USERNAME).is(from_username));
        System.out.println("그럼 여기는? ");
        return mongoTemplate.find(query, ChatRelation.class);

    }


    public ChatRelation getChatRelation(String from_username, String to_username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.FROM_USERNAME).is(from_username)
                .and(KeyConstant.TO_USERNAME).is(to_username));
        return mongoTemplate.findOne(query, ChatRelation.class);
    }


    public void createChatRelation(String from_username, String to_username, Notification noty, List<ChatProps> chat_list) {
        Date time = new Date();
        ChatRelation relation = new ChatRelation();
        relation.setFromUsername(from_username);
        relation.setToUsername(to_username);
        relation.setCreateTime(time);
        relation.setNotification(noty);
        relation.setChatList(chat_list);
        mongoTemplate.insert(relation);
    }


    public void updateChatNotification(String fron_username, String to_username, Notification noty) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.FROM_USERNAME).is(fron_username)
                .and(KeyConstant.TO_USERNAME).is(to_username));

        Update update = new Update();
        update.set(KeyConstant.NOTIFICATION, noty);
        mongoTemplate.upsert(query, update, ChatRelation.class);

    }


    public void updateChat(String fron_username, String to_username, ChatProps chat) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.FROM_USERNAME).is(fron_username)
                .and(KeyConstant.TO_USERNAME).is(to_username));
        ChatRelation chat_relate = mongoTemplate.findOne(query, ChatRelation.class);
        assert chat_relate != null;
        List<ChatProps> chat_list = chat_relate.getChatList();
//        System.out.println(chat_list.get(0).getChatContent());
        chat_list.add(chat);

//        System.out.println("working?");
//        System.out.println(chat_list.get(0).getChatContent());
//        //System.out.println(chat_list.get(1).getChatContent());
//
        Update update = new Update();
        update.set(KeyConstant.CHAT, chat_list);

        mongoTemplate.upsert(query, update, ChatRelation.class);


    }

//    public List<ChatProps> getAllChat(String my_username, String to_username) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where(KeyConstant.MY_USERNAME).is(my_username)
//                .and(KeyConstant.TO_USERNAME).is(to_username));
//        query.with(Sort.by(Sort.Order.asc(KeyConstant.TIME)));
//        return mongoTemplate.find(query, ChatProps.class);
//    }


    public void deleteChat(String from_username, String to_username, String chatId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.FROM_USERNAME).is(from_username)
                .and(KeyConstant.TO_USERNAME).is(to_username));
              //  .and(KeyConstant.CHATID).is(chatId));
      ChatRelation chat = mongoTemplate.findOne(query, ChatRelation.class);
      //System.out.println(chat.getChatList().get(0).getChatContent());
        System.out.println(chat);
        for(int i =0; i<chat.getChatList().size();i++){
            if(chat.getChatList().get(i).getChatId().equals(chatId)){
                chat.getChatList().remove(i);
            }
        }
        Update update = new Update();
        update.set(KeyConstant.CHAT, chat);

        mongoTemplate.upsert(query, update, ChatRelation.class);
    }





    public void deleteRelation(String username, String contact_username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.FROM_USERNAME).is(username)
                .and(KeyConstant.TO_USERNAME).is(contact_username));
        mongoTemplate.remove(query, ChatRelation.class);
    }

}
