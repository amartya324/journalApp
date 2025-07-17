package net.learnspringboot.journalApp.config;

import net.learnspringboot.journalApp.filter.jwtFilter;
import net.learnspringboot.journalApp.service.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurity {


    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private jwtFilter jwtFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//
//        return http.authorizeHttpRequests(request -> request
//                        .requestMatchers( "/user").permitAll() // Allow POST /user without auth (This allows new users to register without being authenticated)
//                        .requestMatchers("/public/**").permitAll()
//                        //.requestMatchers("/journal/**", "/user/**").authenticated()
//                        .requestMatchers("/journal/**").authenticated()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();
//    }
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http.authorizeHttpRequests(request -> request
                            .requestMatchers("/user").permitAll()
                            .requestMatchers("/public/**").permitAll()
                            .requestMatchers("/journal/**").authenticated()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated())
                   //.httpBasic(Customizer.withDefaults())
                    .csrf(AbstractHttpConfigurer::disable)
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }




//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
//package net.learnspringboot.journalApp.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurity {
//    @Override
//    protected  void configure (HttpSecurity http) throws Exception{
//        http.authorizeRequests()
//                .antMatchers("/journal/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .httpBasic();
//        http.csrf().disable();
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//        throws Exception{
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//}
