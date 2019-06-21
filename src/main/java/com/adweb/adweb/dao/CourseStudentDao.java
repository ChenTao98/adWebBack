package com.adweb.adweb.dao;

import com.adweb.adweb.entity.Section;
import com.adweb.adweb.entity.courseStudent.CourseStudentSelect;
import com.adweb.adweb.entity.courseStudent.StudentChoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseStudentDao {
    List<CourseStudentSelect> getCourseSelectByCourseId(Integer courseId);
    StudentChoice studentChoice(@Param("sectionId") Integer sectionId,@Param("studentId") String studentId);
}
