package com.dgpalife.emailcollection.service.impl;

import com.dgpalife.emailcollection.common.Page;
import com.dgpalife.emailcollection.mapper.NumberMapper;
import com.dgpalife.emailcollection.model.Number;
import com.dgpalife.emailcollection.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NumberServiceImpl implements NumberService {

    @Autowired
    private NumberMapper numberMapper;

    @Override
    public Page<Number> selectNumberList(Map<String, Object> params) {
        Page<Number> page = new Page<>((Integer) params.get("pageno"),(Integer)params.get("pagesize"));
        //查询用户列表数据
        params.put("startline",page.getStartline());
        List<Number> datas = numberMapper.selectNumberList(params);

        page.setDatalist(datas);

        //查询电话总数
        Integer totalsize = numberMapper.selectCount(params);
        //将查询结果存放到公共的Page类中
        page.setTotalsize(totalsize);
        return page;
    }

    @Override
    public Number selectNumberByID(Long id) {
        return numberMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateNumber(Number number) {
        return numberMapper.updateByPrimaryKeySelective(number);
    }

    @Override
    public Integer saveNumber(Number number) {
        return numberMapper.insertSelective(number);
    }
}
