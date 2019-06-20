package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.KnowledgeDao;
import com.adweb.adweb.entity.Knowledge;
import com.adweb.adweb.entity.KnowledgeExample;
import com.adweb.adweb.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    private KnowledgeDao knowledgeDao;
    @Override
    public List<Knowledge> getKnowledgeBySection(Integer sectionId) {
        KnowledgeExample knowledgeExample=new KnowledgeExample();
        knowledgeExample.createCriteria().andSectionIdEqualTo(sectionId);
        return knowledgeDao.selectByExample(knowledgeExample);
    }

    @Override
    public int insertKnowledge(Knowledge knowledge) {
        return knowledgeDao.insertSelective(knowledge);
    }

    @Override
    public int insertKnowledge(List<Knowledge> knowledgeList) {
        int count=0;
        for (Knowledge knowledge : knowledgeList) {
            count += knowledgeDao.insertSelective(knowledge);
        }
        return count;
    }
}
