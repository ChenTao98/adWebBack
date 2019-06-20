package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.TeacherDao;
import com.adweb.adweb.entity.Teacher;
import com.adweb.adweb.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    @Override
    public boolean addTeacher(Teacher teacher) {
        return teacherDao.insertSelective(teacher) == 1;
    }
}
