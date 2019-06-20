package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.SectionDao;
import com.adweb.adweb.entity.Section;
import com.adweb.adweb.entity.SectionExample;
import com.adweb.adweb.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    private SectionDao sectionDao;
    @Override
    public List<Section> getSectionByChapter(Integer chapterId) {
        SectionExample sectionExample=new SectionExample();
        sectionExample.createCriteria().andChapterIdEqualTo(chapterId);
        return sectionDao.selectByExample(sectionExample);
    }

    @Override
    public int insertSection(Section section) {
        return sectionDao.insertSelective(section);
    }

    @Override
    public int getLargestSectionOrderNumber(Integer chapter) {
        SectionExample sectionExample=new SectionExample();
        sectionExample.createCriteria().andChapterIdEqualTo(chapter);
        sectionExample.setOrderByClause("order_number desc");
        List<Section> list=sectionDao.selectByExample(sectionExample);
        if(list.size()!=0){
            return list.get(0).getOrderNumber();
        }
        return 0;
    }

    @Override
    public Section isSectionBelongToTeacher(Integer sectionId, String teacherId) {
        return sectionDao.isSectionBelongToTeacher(sectionId, teacherId);
    }
}
