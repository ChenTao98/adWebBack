package com.adweb.adweb.dao;

import com.adweb.adweb.entity.Course;
import com.adweb.adweb.entity.CourseExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface CourseDao {
    long countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    Course getCourseByChapter(Integer chapterId);

    Course getCourseBySection(Integer sectionId);

    Course getCourseDetailById(@Param("courseId") Integer courseId, @Param("studentId") String studentId);

    int deleteOption(Integer courseId);

    int deleteChoice(Integer courseId);

    int deleteKnowledge(Integer courseId);

    int deleteSection(Integer courseId);
}