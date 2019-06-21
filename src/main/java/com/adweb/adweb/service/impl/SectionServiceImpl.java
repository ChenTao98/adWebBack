package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.ChoiceQuestionDao;
import com.adweb.adweb.dao.KnowledgeDao;
import com.adweb.adweb.dao.OptionDao;
import com.adweb.adweb.dao.SectionDao;
import com.adweb.adweb.entity.ChoiceQuestionExample;
import com.adweb.adweb.entity.KnowledgeExample;
import com.adweb.adweb.entity.Section;
import com.adweb.adweb.entity.SectionExample;
import com.adweb.adweb.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private KnowledgeDao knowledgeDao;
    @Autowired
    private ChoiceQuestionDao choiceQuestionDao;
    @Autowired
    private OptionDao optionDao;

    @Override
    public List<Section> getSectionByChapter(Integer chapterId) {
        SectionExample sectionExample = new SectionExample();
        sectionExample.createCriteria().andChapterIdEqualTo(chapterId);
        sectionExample.setOrderByClause("order_number");
        return sectionDao.selectByExample(sectionExample);
    }

    @Override
    public int insertSection(Section section) {
        return sectionDao.insertSelective(section);
    }

    @Override
    public int getLargestSectionOrderNumber(Integer chapter) {
        SectionExample sectionExample = new SectionExample();
        sectionExample.createCriteria().andChapterIdEqualTo(chapter);
        sectionExample.setOrderByClause("order_number desc");
        List<Section> list = sectionDao.selectByExample(sectionExample);
        if (list.size() != 0) {
            return list.get(0).getOrderNumber();
        }
        return 0;
    }

    @Override
    public Section isSectionBelongToTeacher(Integer sectionId, String teacherId) {
        return sectionDao.isSectionBelongToTeacher(sectionId, teacherId);
    }

    @Override
    @Transactional
    public int modifySection(Section section, Integer oldNumber, Integer chapter) {
        int orderNumber;
        if (section.getOrderNumber() != null) {
            int largestNumber = getLargestSectionOrderNumber(chapter);
            orderNumber = largestNumber < section.getOrderNumber() ? largestNumber : section.getOrderNumber();
            if (orderNumber > oldNumber) {
                sectionDao.updateSectionSmaller(oldNumber, orderNumber, chapter);
            } else if (orderNumber < oldNumber) {
                sectionDao.updateSectionLarger(orderNumber, oldNumber, chapter);
            }
            section.setOrderNumber(orderNumber);
        }
        return sectionDao.updateByPrimaryKeySelective(section);
    }

    @Override
    @Transactional
    public int deleteSection(Section section) {
        int sectionId = section.getId();
        int chapterId = section.getChapterId();
        KnowledgeExample knowledgeExample = new KnowledgeExample();
        knowledgeExample.createCriteria().andSectionIdEqualTo(sectionId);
        knowledgeDao.deleteByExample(knowledgeExample);
        optionDao.deleteInQuestionId(sectionId);
        ChoiceQuestionExample choiceQuestionExample = new ChoiceQuestionExample();
        choiceQuestionExample.createCriteria().andSectionIdEqualTo(sectionId);
        choiceQuestionDao.deleteByExample(choiceQuestionExample);
        sectionDao.deleteByPrimaryKey(sectionId);
        sectionDao.updateSectionSmaller(section.getOrderNumber(), getLargestSectionOrderNumber(chapterId), chapterId);
        return 1;
    }

    @Override
    public Section getSectionBySectionId(Integer sectionId) {
        return sectionDao.selectByPrimaryKey(sectionId);
    }
}
