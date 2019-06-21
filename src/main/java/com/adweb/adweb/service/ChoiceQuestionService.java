package com.adweb.adweb.service;

import com.adweb.adweb.entity.ChoiceQuestion;

import java.util.List;

public interface ChoiceQuestionService {
    List<ChoiceQuestion> getQuestionWithOptionBySection(Integer sectionId);
    int insertQuestion(ChoiceQuestion choiceQuestion);
    int deleteChoice(Integer questionId);
}
