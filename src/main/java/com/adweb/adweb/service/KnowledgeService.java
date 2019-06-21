package com.adweb.adweb.service;

import com.adweb.adweb.entity.Knowledge;
import com.adweb.adweb.entity.Section;

import java.util.List;

public interface KnowledgeService {
    List<Knowledge> getKnowledgeBySection(Integer sectionId);
    int insertKnowledge(Knowledge knowledge);
    int insertKnowledge(List<Knowledge> knowledgeList);
    int getLargestKnowledgeOrderNumber(Integer section);
    int deleteKnowledge(Integer sectionId, Knowledge knowledge);
    Knowledge getKnowledgeById(Integer knowledgeId);
}
