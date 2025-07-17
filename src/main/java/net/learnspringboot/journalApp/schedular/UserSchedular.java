package net.learnspringboot.journalApp.schedular;

import net.learnspringboot.journalApp.cache.AppCache;
import net.learnspringboot.journalApp.entity.JournalEntry;
import net.learnspringboot.journalApp.entity.User;
import net.learnspringboot.journalApp.enums.Sentiment;
import net.learnspringboot.journalApp.model.SentimentData;
import net.learnspringboot.journalApp.repository.UserRepositoryImpl;
import net.learnspringboot.journalApp.service.EmailService;
import net.learnspringboot.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {
    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private KafkaTemplate<String,SentimentData> kafkaTemplate;

    //@Scheduled(cron = "0 * * ? * *")
   // @Scheduled(cron = "8 9 * * * SUN")
     public void fetchUsersAndSendSAMail(){
         List<User> users =userRepository.getUserForSA();
         for(User user : users) {
             List<JournalEntry> journalEntries = user.getJournalEntries();
             List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
             Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
             for (Sentiment sentiment : sentiments) {
                 if (sentiment != null)
                     sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
             }
             Sentiment mostFrequentSentiment = null;
             int maxCount = 0;
             for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                 if (entry.getValue() > maxCount) {
                     maxCount = entry.getValue();
                     mostFrequentSentiment = entry.getKey();

                 }
             }
//             String entry = String.join("", filteredEntries);
//             String sentiment = sentimentAnalysisService.getSentiment(entry);
             if(mostFrequentSentiment != null){
               //  emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days", mostFrequentSentiment.toString());
                 SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+mostFrequentSentiment).build();
                // kafkaTemplate.send("weekly-sentiment",sentimentData.getEmail(),sentimentData);
             }

         }
     }

     @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
     }
}
