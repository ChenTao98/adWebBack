package com.adweb.adweb.service;

import com.adweb.adweb.entity.Student;
import com.adweb.adweb.entity.courseStudent.CourseStudentSelect;
import com.adweb.adweb.entity.courseStudent.StudentChoice;

import java.util.List;

public interface CourseStudentService {
    List<CourseStudentSelect> getCourseSelectByCourseId(Integer courseId);
    Student getStudentById(String id);
    StudentChoice studentChoice(Integer sectionId,String studentId);
}
