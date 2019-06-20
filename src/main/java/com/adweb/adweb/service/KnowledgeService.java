package com.adweb.adweb.service;

import com.adweb.adweb.entity.Knowledge;

import java.util.List;

public interface KnowledgeService {
    List<Knowledge> getKnowledgeBySection(Integer sectionId);
    int insertKnowledge(Knowledge knowledge);
    int insertKnowledge(List<Knowledge> knowledgeList);
}
