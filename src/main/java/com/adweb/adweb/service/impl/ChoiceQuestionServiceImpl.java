package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.ChoiceQuestionDao;
import com.adweb.adweb.dao.OptionDao;
import com.adweb.adweb.entity.ChoiceQuestion;
import com.adweb.adweb.entity.Option;
import com.adweb.adweb.entity.OptionExample;
import com.adweb.adweb.service.ChoiceQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ChoiceQuestionServiceImpl implements ChoiceQuestionService {
    @Autowired
    private ChoiceQuestionDao choiceQuestionDao;
    @Autowired
    private OptionDao optionDao;
    @Override
    public List<ChoiceQuestion> getQuestionWithOptionBySection(Integer sectionId) {
        return choiceQuestionDao.getQuestionWithOptionBySection(sectionId);
    }

    @Override
    @Transactional
    public int insertQuestion(ChoiceQuestion choiceQuestion) {
        if(choiceQuestionDao.insertSelective(choiceQuestion)==1){
            List<Option> list=choiceQuestion.getOptionList();
            for (Option option : list) {
                option.setQuestionId(choiceQuestion.getId());
                optionDao.insertSelective(option);
            }
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int deleteChoice(Integer questionId) {
        OptionExample optionExample=new OptionExample();
        optionExample.createCriteria().andQuestionIdEqualTo(questionId);
        optionDao.deleteByExample(optionExample);
        return choiceQuestionDao.deleteByPrimaryKey(questionId);
    }
}
