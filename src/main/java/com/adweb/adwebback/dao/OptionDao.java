package com.adweb.adwebback.dao;

import com.adweb.adwebback.entity.Option;
import com.adweb.adwebback.entity.OptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OptionDao {
    long countByExample(OptionExample example);

    int deleteByExample(OptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Option record);

    int insertSelective(Option record);

    List<Option> selectByExample(OptionExample example);

    Option selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Option record, @Param("example") OptionExample example);

    int updateByExample(@Param("record") Option record, @Param("example") OptionExample example);

    int updateByPrimaryKeySelective(Option record);

    int updateByPrimaryKey(Option record);
}