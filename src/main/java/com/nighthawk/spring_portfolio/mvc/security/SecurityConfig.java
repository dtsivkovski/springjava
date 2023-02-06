package com.nighthawk.spring_portfolio.mvc.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.nighthawk.spring_portfolio.mvc.jwt.JwtAuthenticationEntryPoint;
import com.nighthawk.spring_portfolio.mvc.jwt.JwtUserDetailsService;
import com.nighthawk.spring_portfolio.mvc.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor //RequiredArgsConstructor makes the line below be able to pass that variable in on the fly
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //private final UserDetailsService userDetailsService; //constructor that gets past in below
    private final BCryptPasswordEncoder BCryptPasswordEncoder; //password encoder from spring

    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder()); //
    }

    public PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Override
<<<<<<< HEAD
    protected void configure(HttpSecurity http) throws Exception {
        /* security rules ...
         *   ... initial implementation is focused on protecting database information
         *   ... "DELETE" is primary concern in authority rules, ADMIN only
         *   ... "POST", actions desire STUDENT role
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
=======
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity.csrf().disable();
		httpSecurity
		        // We don't need CSRF for this example
                .csrf().disable()
				// don't authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate", "/register").permitAll()
				// all other requests need to be authenticated
				.anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

    
}
>>>>>>> ccc423795ff5f451d60856ab77f2c2bb22ae04fa
