package com.adweb.adweb.service;

import com.adweb.adweb.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourseByTeacher(String teacherId);
    int insertCourse(Course course);
}
