package net.learnspringboot.journalApp.service;
//package net.learnspringboot.journalApp.service;
//
//import net.learnspringboot.journalApp.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        net.learnspringboot.journalApp.entity.User user = userRepository.findByUserName(username);
//        if (user != null) {
//            return User.builder()
//                    .username(user.getUserName())
//                    .password(user.getPassword())
//                    .roles(user.getRoles().toArray(new String[0]))
//                    .build();
//        }
//        throw new UsernameNotFoundException("User not found with username: " + username);
//    }
//}


import net.learnspringboot.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        net.learnspringboot.journalApp.entity.User user = userRepository.findByUserName(username);
        if(user != null){
            return User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: "+username);
    }
}