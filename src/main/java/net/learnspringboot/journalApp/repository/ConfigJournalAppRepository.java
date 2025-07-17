package net.learnspringboot.journalApp.repository;

import net.learnspringboot.journalApp.entity.ConfigJournalAppEntity;
import net.learnspringboot.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
