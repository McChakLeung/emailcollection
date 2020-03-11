package com.dgpalife.emailcollection.controller;

import com.dgpalife.emailcollection.common.AjaxResult;
import com.dgpalife.emailcollection.common.Page;
import com.dgpalife.emailcollection.common.StringUtil;
import com.dgpalife.emailcollection.model.Number;
import com.dgpalife.emailcollection.service.NumberService;
import org.apache.ibatis.jdbc.Null;
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
public class NumberController {

    @Autowired
    private NumberService numberService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "/index";
    }

    @ResponseBody
    @RequestMapping("/index")
    public Object toIndex(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize",required = false,defaultValue = "20") Integer pagesize,
                          @RequestParam(value = "operator",required = false) String operator,
                          @RequestParam(value = "parentdepartment",required = false) String parentdepartment,
                          @RequestParam(value = "department",required = false) String department,
                          @RequestParam(value = "telephone",required = false) String telephone){

        AjaxResult result = new AjaxResult();

        try{
            Map<String,Object> params = new HashMap<>();
            params.put("pageno",pageno);
            params.put("pagesize",pagesize);
            params.put("operator", operator);
            params.put("parentdepartment", parentdepartment);
            params.put("department", department);
            params.put("telephone", telephone);
//            if(StringUtil.isNotEmpty(parentdepartment)){
//                if(parentdepartment.contains("%")){
//                    parentdepartment = parentdepartment.replaceAll("%", "\\\\%");
//                }
//                params.put("parentdepartment", parentdepartment); //   \%
//            }
//            if(StringUtil.isNotEmpty(department)){
//                if(department.contains("%")){
//                    department = department.replaceAll("%", "\\\\%");
//                }
//                params.put("department", department); //   \%
//            }
//            if(StringUtil.isNotEmpty(telephone)){
//                if(telephone.contains("%")){
//                    telephone = telephone.replaceAll("%", "\\\\%");
//                }
//                params.put("telephone", telephone); //   \%
//            }
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

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Long id){

        AjaxResult result = new AjaxResult();

        try{
            numberService.deleteNumberById(id);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("删除异常，请联系管理员处理");
            e.printStackTrace();
        }
        return result;
    }
}
