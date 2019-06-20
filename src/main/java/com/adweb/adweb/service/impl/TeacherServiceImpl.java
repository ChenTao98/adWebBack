package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.TeacherDao;
import com.adweb.adweb.entity.Teacher;
import com.adweb.adweb.entity.TeacherExample;
import com.adweb.adweb.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    @Override
    public boolean addTeacher(Teacher teacher) {
        return teacherDao.insertSelective(teacher) == 1;
    }

    @Override
    public Teacher getTeacherByEmail(String email) {
        TeacherExample example = new TeacherExample();
        example.createCriteria().andEmailEqualTo(email);
        List<Teacher> teachers =  teacherDao.selectByExample(example);
        if (teachers.size() == 0) {
            return null;
        }
        return teachers.get(0);
    }
}
