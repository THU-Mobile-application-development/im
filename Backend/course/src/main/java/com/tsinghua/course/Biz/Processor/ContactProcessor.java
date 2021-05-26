package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class ContactProcessor {

    @Autowired
    MongoTemplate mongoTemplate;

    /** 根据用户名查找自己的某一位好友 */
    public Contact getRelationByUsername(String username, String friend_username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username)
                .and(KeyConstant.CONTACT_USERNAME).is(friend_username));
        return mongoTemplate.findOne(query, Contact.class);
    }


    /** 根据用户名查找自己的某一位好友 */
    public List<Contact> getAllContact(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        return mongoTemplate.find(query, Contact.class);
    }


    /** 添加好友关系 */
    public void addContact(String username, String add_contact) {
        Contact relation = new Contact();
        relation.setUsername(username);
        relation.setContactUsername(add_contact);

        mongoTemplate.insert(relation);
    }


    public void deleteContact(String username, String contact_username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username)
                .and(KeyConstant.CONTACT_USERNAME).is(contact_username));
        mongoTemplate.remove(query, Contact.class);
    }
}
