package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.ChapterDao;
import com.adweb.adweb.dao.SectionDao;
import com.adweb.adweb.entity.Chapter;
import com.adweb.adweb.entity.ChapterExample;
import com.adweb.adweb.entity.SectionExample;
import com.adweb.adweb.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private SectionDao sectionDao;

    @Override
    public List<Chapter> getChapterByCourse(Integer course) {
        ChapterExample chapterExample = new ChapterExample();
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
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andCourseIdEqualTo(courseId);
        chapterExample.setOrderByClause("order_number desc");
        List<Chapter> list = chapterDao.selectByExample(chapterExample);
        if (list.size() != 0) {
            return list.get(0).getOrderNumber();
        }
        return 0;
    }

    @Override
    public Chapter isChapterBelongToTeacher(Integer chapterId, String teacherId) {
        return chapterDao.isChapterBelongToTeacher(chapterId, teacherId);
    }

    @Override
    public Chapter getChapterByChapterId(Integer chapterId) {
        return chapterDao.selectByPrimaryKey(chapterId);
    }

    @Override
    @Transactional
    public int modifyChapter(Chapter chapter, Integer oldNumber, Integer courseId) {
        int orderNumber;
        if (chapter.getOrderNumber() != null) {
            int largestOrder = getLargestChapterOrderName(courseId);
            orderNumber = largestOrder < chapter.getOrderNumber() ? largestOrder : chapter.getOrderNumber();
            if (orderNumber > oldNumber) {
                chapterDao.updateChapterSmaller(oldNumber,orderNumber,courseId);
            }else if (orderNumber<oldNumber){
                chapterDao.updateChapterLarger(orderNumber,oldNumber,courseId);
            }
            chapter.setOrderNumber(orderNumber);
        }
        return chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    @Transactional
    public int deleteChapter(Chapter chapter) {
        int chapterId=chapter.getId();
        int courseId=chapter.getCourseId();
        chapterDao.deleteOption(chapterId);
        chapterDao.deleteChoice(chapterId);
        chapterDao.deleteKnowledge(chapterId);
        SectionExample sectionExample=new SectionExample();
        sectionExample.createCriteria().andChapterIdEqualTo(chapterId);
        sectionDao.deleteByExample(sectionExample);
        chapterDao.deleteByPrimaryKey(chapterId);
        chapterDao.updateChapterSmaller(chapter.getOrderNumber(),getLargestChapterOrderName(courseId),courseId);
        return 1;
    }
}
