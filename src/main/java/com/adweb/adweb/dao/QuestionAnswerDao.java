package com.adweb.adweb.dao;

import com.adweb.adweb.entity.QuestionAnswerExample;
import com.adweb.adweb.entity.QuestionAnswerKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionAnswerDao {
    long countByExample(QuestionAnswerExample example);

    int deleteByExample(QuestionAnswerExample example);

    int deleteByPrimaryKey(QuestionAnswerKey key);

    int insert(QuestionAnswerKey record);

    int insertSelective(QuestionAnswerKey record);

    List<QuestionAnswerKey> selectByExample(QuestionAnswerExample example);

    int updateByExampleSelective(@Param("record") QuestionAnswerKey record, @Param("example") QuestionAnswerExample example);

    int updateByExample(@Param("record") QuestionAnswerKey record, @Param("example") QuestionAnswerExample example);
}