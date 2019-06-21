package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.ChapterDao;
import com.adweb.adweb.entity.Chapter;
import com.adweb.adweb.entity.ChapterExample;
import com.adweb.adweb.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public List<Chapter> getChapterByCourse(Integer course) {
        ChapterExample chapterExample=new ChapterExample();
        chapterExample.createCriteria().andCourseIdEqualTo(course);
        chapterExample.setOrderByClause("order_number");
        return chapterDao.selectByExample(chapterExample);
    }

    @Override
    public int insertChapter(Chapter chapter) {
        return chapterDao.insertSelective(chapter);
    }

    @Override
    public int getLargestChapterOrderName(Integer courseId) {
        ChapterExample chapterExample=new ChapterExample();
        chapterExample.createCriteria().andCourseIdEqualTo(courseId);
        chapterExample.setOrderByClause("order_number desc");
        List<Chapter> list=chapterDao.selectByExample(chapterExample);
        if(list.size()!=0){
            return list.get(0).getOrderNumber();
        }
        return 0;
    }

    @Override
    public Chapter isChapterBelongToTeacher(Integer chapterId, String teacherId) {
        return chapterDao.isChapterBelongToTeacher(chapterId,teacherId);
    }

    @Override
    public Chapter getChapterByChapterId(Integer chapterId) {
        return chapterDao.selectByPrimaryKey(chapterId);
    }
}
