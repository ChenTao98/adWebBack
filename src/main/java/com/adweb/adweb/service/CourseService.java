package com.adweb.adweb.service;

import com.adweb.adweb.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourseByTeacher(String teacherId);

    int insertCourse(Course course);

    Course getCourseById(Integer integer, String teacherId);

    Course getCourseByChapter(Integer chapter);

    Course getCourseBySection(Integer section);

    Course getCourseByStudent(Integer id, String studentID);

    int modifyCourse(Course course);

    int deleteCourse(Course course);
}
