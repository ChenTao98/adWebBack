package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.CourseStudentDao;
import com.adweb.adweb.dao.StudentDao;
import com.adweb.adweb.entity.Student;
import com.adweb.adweb.entity.courseStudent.CourseStudentSelect;
import com.adweb.adweb.entity.courseStudent.StudentChoice;
import com.adweb.adweb.service.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseStudentServiceImpl implements CourseStudentService {
    @Autowired
    private CourseStudentDao courseStudentDao;
    @Autowired
    private StudentDao studentDao;
    @Override
    public List<CourseStudentSelect> getCourseSelectByCourseId(Integer courseId) {
        return courseStudentDao.getCourseSelectByCourseId(courseId);
    }

    @Override
    public Student getStudentById(String id) {
        return studentDao.selectByPrimaryKey(id);
    }

    @Override
    public StudentChoice studentChoice(Integer sectionId, String studentId) {
        return courseStudentDao.studentChoice(sectionId, studentId);
    }
}
