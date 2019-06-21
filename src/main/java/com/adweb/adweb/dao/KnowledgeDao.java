package com.adweb.adweb.dao;

import com.adweb.adweb.entity.Knowledge;
import com.adweb.adweb.entity.KnowledgeExample;

import java.util.List;

import com.adweb.adweb.entity.Section;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;

public interface KnowledgeDao {
    long countByExample(KnowledgeExample example);

    int deleteByExample(KnowledgeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    List<Knowledge> selectByExample(KnowledgeExample example);

    Knowledge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Knowledge record, @Param("example") KnowledgeExample example);

    int updateByExample(@Param("record") Knowledge record, @Param("example") KnowledgeExample example);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);

    int updateKnowledgeSmaller(@Param("small") Integer small, @Param("large") Integer large, @Param("sectionId") Integer sectionId);

    int updateKnowledgeLarger(@Param("small") Integer small, @Param("large") Integer large, @Param("sectionId") Integer sectionId);
}