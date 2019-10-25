package com.dgpalife.emailcollection.mapper;

import com.dgpalife.emailcollection.common.Page;
import com.dgpalife.emailcollection.model.Number;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@Mapper
public interface NumberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Number record);

    int insertSelective(Number record);

    Number selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Number record);

    int updateByPrimaryKey(Number record);


    List<Number> selectNumberList(Map<String, Object> params);

    Integer selectCount(Map<String, Object> params);
}