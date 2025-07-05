package net.learnspringboot.journalApp.repository;

import net.learnspringboot.journalApp.entity.JournalEntry;
import net.learnspringboot.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
    void deleteByUserName(String username);
}
