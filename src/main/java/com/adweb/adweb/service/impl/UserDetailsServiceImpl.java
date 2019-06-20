package com.adweb.adweb.service.impl;

import com.adweb.adweb.entity.Teacher;
import com.adweb.adweb.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private TeacherService teacherService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Teacher teacher = teacherService.getTeacherByEmail(s);
        if (teacher == null) {
            LOG.info("user: {} not found" , s);
            throw new UsernameNotFoundException("user: " + s + " not found!");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+"ADMIN"));
        LOG.info("user: {} role: {} 尝试登陆", teacher.getOpenId(), "ADMIN");
        return new User(teacher.getOpenId(), teacher.getPassword(), authorities);
    }

}
