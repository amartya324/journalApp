package net.learnspringboot.journalApp.repository;

import net.learnspringboot.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> getUserForSA(){
        Query query = new Query();
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("email").regex(emailPattern));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        //query.addCriteria(Criteria.where("userName").is("vipul"));
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
