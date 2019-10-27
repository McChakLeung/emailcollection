package com.dgpalife.emailcollection.service;

import com.dgpalife.emailcollection.common.Page;
import com.dgpalife.emailcollection.model.Number;

import java.util.Map;

public interface NumberService {
    Page<Number> selectNumberList(Map<String, Object> params);

    Number selectNumberByID(Long id);

    Integer updateNumber(Number number);

    Integer saveNumber(Number number);

    void deleteNumberById(Long id);
}
