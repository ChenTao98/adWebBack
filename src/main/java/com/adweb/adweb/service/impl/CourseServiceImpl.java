package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.ChapterDao;
import com.adweb.adweb.dao.CourseDao;
import com.adweb.adweb.entity.ChapterExample;
import com.adweb.adweb.entity.Course;
import com.adweb.adweb.entity.CourseExample;
import com.adweb.adweb.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ChapterDao chapterDao;

    @Override
    public List<Course> getCourseByTeacher(String teacherId) {
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andTeacherIdEqualTo(teacherId);
        courseExample.setOrderByClause("order_number");
        return courseDao.selectByExample(courseExample);
    }

    @Override
    public int insertCourse(Course course) {
        return courseDao.insertSelective(course);
    }

    @Override
    public Course getCourseById(Integer courseId, String teacherId) {
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andIdEqualTo(courseId).andTeacherIdEqualTo(teacherId);
        List<Course> list = courseDao.selectByExample(courseExample);
        if (list.size() != 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Course getCourseByChapter(Integer chapter) {
        return courseDao.getCourseByChapter(chapter);
    }

    @Override
    public Course getCourseBySection(Integer section) {
        return courseDao.getCourseBySection(section);
    }

    @Override
    public Course getCourseByStudent(Integer id, String studentId) {
        return courseDao.getCourseDetailById(id, studentId);
    }

    @Override
    public int modifyCourse(Course course) {
        return courseDao.updateByPrimaryKeySelective(course);
    }

    @Override
    public int deleteCourse(Course course) {
        int courseId = course.getId();
        courseDao.deleteOption(courseId);
        courseDao.deleteChoice(courseId);
        courseDao.deleteKnowledge(courseId);
        courseDao.deleteSection(courseId);
        ChapterExample chapterExample=new ChapterExample();
        chapterExample.createCriteria().andCourseIdEqualTo(courseId);
        chapterDao.deleteByExample(chapterExample);
        return courseDao.deleteByPrimaryKey(courseId);
    }
}
