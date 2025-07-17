package net.learnspringboot.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail() {
        emailService.sendEmail("amartya324@gmail.com", "Testing Java Email", "This is a test email.");
    }

}
