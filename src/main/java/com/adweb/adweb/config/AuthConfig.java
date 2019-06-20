package com.adweb.adweb.config;

import com.adweb.adweb.service.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthConfig.class);
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        PasswordEncoder passwordEncoder = passwordEncoder();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     *  对角色进行授权
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/course/**").hasRole("ADMIN")
                .antMatchers("/myinfo/**").hasRole("ADMIN")
                .antMatchers("/student/**").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/login").successHandler( (req, res, auth) -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                UserDetails user = (UserDetails)principal;
                System.out.println("User Login: " + user.getUsername());
                req.getSession().setAttribute("openId", user.getUsername());
                res.sendRedirect("/course");
            }
        }).permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.sendRedirect("/login");
                }).invalidateHttpSession(true)
                .addLogoutHandler((req, res, auth)-> {
                    LOGGER.info("logout...");
                }).permitAll()
                .and()
                .csrf().disable();
    }


    /**
     *  添加自定义用户，并添加角色
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}
