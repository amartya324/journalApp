package net.learnspringboot.journalApp.service;

import net.learnspringboot.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private JournalEntryService journalEntryService;

    @Test
    public void testFindByUserName(){
        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUserName("ram"));
        assertTrue(5>3);
    }
}
