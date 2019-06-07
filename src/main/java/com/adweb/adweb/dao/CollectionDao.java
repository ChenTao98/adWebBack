package com.adweb.adweb.dao;

import com.adweb.adweb.entity.CollectionExample;
import com.adweb.adweb.entity.CollectionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectionDao {
    long countByExample(CollectionExample example);

    int deleteByExample(CollectionExample example);

    int deleteByPrimaryKey(CollectionKey key);

    int insert(CollectionKey record);

    int insertSelective(CollectionKey record);

    List<CollectionKey> selectByExample(CollectionExample example);

    int updateByExampleSelective(@Param("record") CollectionKey record, @Param("example") CollectionExample example);

    int updateByExample(@Param("record") CollectionKey record, @Param("example") CollectionExample example);
}