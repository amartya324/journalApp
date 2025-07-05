package net.learnspringboot.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.learnspringboot.journalApp.entity.JournalEntry;
import net.learnspringboot.journalApp.entity.User;
import net.learnspringboot.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import  org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
//import java.util.logging.Logger;

@Slf4j
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
           // user.setUserName(null);
            userService.saveUser(user);
        }
        catch (Exception e){
            System.out.println(e);

            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId Id) {
        return journalEntryRepository.findById(Id);
    }

    @Transactional
    @Slf4j
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Error: ",e);
            throw new RuntimeException("An error occurred while deleting the entry.",e);
        }
        return removed;
    }
//    public List<JournalEntry> findByUserName(String userName){
//
//    }

}


