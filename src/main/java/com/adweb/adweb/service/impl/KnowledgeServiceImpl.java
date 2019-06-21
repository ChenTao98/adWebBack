package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.KnowledgeDao;
import com.adweb.adweb.entity.Knowledge;
import com.adweb.adweb.entity.KnowledgeExample;
import com.adweb.adweb.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    private KnowledgeDao knowledgeDao;

    @Override
    public List<Knowledge> getKnowledgeBySection(Integer sectionId) {
        KnowledgeExample knowledgeExample = new KnowledgeExample();
        knowledgeExample.createCriteria().andSectionIdEqualTo(sectionId);
        knowledgeExample.setOrderByClause("order_number");
        return knowledgeDao.selectByExample(knowledgeExample);
    }

    @Override
    public int insertKnowledge(Knowledge knowledge) {
        int sectionId = knowledge.getSectionId();
        int order = knowledge.getOrderNumber();
        int nextOrder = getLargestKnowledgeOrderNumber(sectionId) + 1;
        order = nextOrder <= order ? nextOrder : order;
        knowledge.setOrderNumber(order);
        if (order == nextOrder) {
            knowledgeDao.insertSelective(knowledge);
        } else {
            knowledgeDao.updateKnowledgeLarger(order, nextOrder - 1, sectionId);
            knowledgeDao.insertSelective(knowledge);
        }
        return 1;
    }

    @Override
    @Transactional
    public int insertKnowledge(List<Knowledge> knowledgeList) {
        int count = 0;
        for (Knowledge knowledge : knowledgeList) {
            count += knowledgeDao.insertSelective(knowledge);
        }
        return count;
    }

    @Override
    public int getLargestKnowledgeOrderNumber(Integer section) {
        KnowledgeExample knowledgeExample = new KnowledgeExample();
        knowledgeExample.createCriteria().andSectionIdEqualTo(section);
        knowledgeExample.setOrderByClause("order_number desc");
        List<Knowledge> list = knowledgeDao.selectByExample(knowledgeExample);
        if (list.size() != 0) {
            return list.get(0).getOrderNumber();
        }
        return 0;
    }

    @Override
    @Transactional
    public int deleteKnowledge(Integer sectionId, Knowledge knowledge) {
        int knowledgeId = knowledge.getId();
        knowledgeDao.updateKnowledgeSmaller(knowledge.getOrderNumber(), getLargestKnowledgeOrderNumber(sectionId), sectionId);
        return knowledgeDao.deleteByPrimaryKey(knowledgeId);
    }

    @Override
    public Knowledge getKnowledgeById(Integer knowledgeId) {
        return knowledgeDao.selectByPrimaryKey(knowledgeId);
    }
}
