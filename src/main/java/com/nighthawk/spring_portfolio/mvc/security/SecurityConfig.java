package com.nighthawk.spring_portfolio.mvc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor //RequiredArgsConstructor makes the line below be able to pass that variable in on the fly
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService; //constructor that gets past in below
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //password encoder from spring

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder); //
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* security rules ...
            ... initial implementation is focused on protecting database information
            ... "DELETE" is primary concern in authority rules, ADMIN only
            ... "POST", actions desire STUDENT role
         */
        http
            .authorizeRequests()
                .antMatchers(POST, "/api/person/post/**").hasAnyAuthority("ROLE_STUDENT")
                .antMatchers(DELETE, "/api/person/delete/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/database/personupdate/**").hasAnyAuthority("ROLE_STUDENT")
                .antMatchers("/database/persondelete/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers( "/api/person/**").permitAll()
                .antMatchers( "/api/refresh/token/**").permitAll()
                .antMatchers("/", "/starters/**", "/frontend/**", "/mvc/**", "/database/person/**", "/database/personcreate", "/database/scrum/**", "/course/**").permitAll()
                .antMatchers("/resources/**", "/static/**",  "/images/**", "/scss/**").permitAll()
                .antMatchers("/volumes/uploads/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/database/person")
                .permitAll()
        ;
        // Cross-Site Request Forgery needs to be disabled to allow activation of JS Fetch URIs
        http.csrf().disable();
    }
}