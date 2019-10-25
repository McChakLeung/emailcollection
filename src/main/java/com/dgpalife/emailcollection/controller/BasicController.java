package com.dgpalife.emailcollection.controller;

import com.dgpalife.emailcollection.common.AjaxResult;
import com.dgpalife.emailcollection.common.Page;
import com.dgpalife.emailcollection.common.StringUtil;
import com.dgpalife.emailcollection.model.Number;
import com.dgpalife.emailcollection.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/")
public class BasicController {

    @Autowired
    private NumberService numberService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "/index";
    }

    @ResponseBody
    @RequestMapping("/index")
    public Object toIndex(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize,
                          String queryText){

        AjaxResult result = new AjaxResult();

        try{
            Map<String,Object> params = new HashMap<>();
            params.put("pageno",pageno);
            params.put("pagesize",pagesize);
            if(StringUtil.isNotEmpty(queryText)){
                if(queryText.contains("%")){
                    queryText = queryText.replaceAll("%", "\\\\%");
                }
                params.put("queryText", queryText); //   \%
            }
            Page<Number> page = numberService.selectNumberList(params);
            if(page == null){
                result.setSuccess(false);
                result.setMessage("无相关记录");
                return result;
            }
            result.setPage(page);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("查询异常，请联系管理员处理");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model) {
        Number number = numberService.selectNumberByID(id);
        model.addAttribute("number",number);
        return "/update";
    }

    //更新操作
    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Number number){

        AjaxResult result = new AjaxResult();

        try{
            Integer count = numberService.updateNumber(number);
            result.setSuccess(count>0);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("更新异常，请联系管理员处理");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "/add";
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(Number number){

        AjaxResult result = new AjaxResult();

        try{
            Integer count = numberService.saveNumber(number);
            result.setSuccess(count>0);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("保存异常，请联系管理员处理");
            e.printStackTrace();
        }
        return result;
    }
}
