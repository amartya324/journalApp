//package net.learnspringboot.journalApp.controller;
//
//import net.learnspringboot.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntries.values());
//    }
//    @PostMapping
//    public Boolean createEntry(@RequestBody JournalEntry myEntry){
//        journalEntries.put(myEntry.getId(),myEntry);
//        return true;
//    }
//    @GetMapping("id/{myId}")
//    public JournalEntry getJournalEntryById(@PathVariable Long myId){
//        return journalEntries.get(myId);
//    }
//    @DeleteMapping("id/{myId}")
//    public JournalEntry deleteJournalEntryById(@PathVariable Long myId){
//        return journalEntries.remove(myId);
//    }
//    @PutMapping("id/{id}")
//    public JournalEntry updateJournalById(@PathVariable Long id,@RequestBody JournalEntry myEntry){
//        return journalEntries.put(id,myEntry);
//    }
//
//}
