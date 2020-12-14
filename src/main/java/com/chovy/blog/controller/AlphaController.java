package com.chovy.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {


    @RequestMapping("/hello")
    @ResponseBody
    public String sayhello(){
        return "Hello Spring Boot!";
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name+":"+value);
        }
        System.out.println(request.getParameter("code"));


    }
    //保存学生信息
    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name+","+age+"保存成功");
        return "ok";
    }
    //响应HTML数据
    //简洁一些
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","麻省理工");
        model.addAttribute("age","200");
        return "/demo/view";
    }
    //响应 Json数据(异步请求)
    //Java对象-->Json字符串-->Js对象
    @RequestMapping(path = "/staff",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getStaff(){
        Map<String, Object> staffMap = new HashMap<>();
        staffMap.put("name","张三");
        staffMap.put("age",30);
        staffMap.put("sallary",5000.00);
        return staffMap;
    }
    @RequestMapping(path="/staffs",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getStaffs(){
        ArrayList<Map<String,Object>> list = new ArrayList<>();

        Map<String, Object> staffMap = new HashMap<>();
        staffMap.put("name","张三");
        staffMap.put("age",30);
        staffMap.put("sallary",5000.00);
        list.add(staffMap);

        staffMap=new HashMap<>();
        staffMap.put("name","李四");
        staffMap.put("age",31);
        staffMap.put("sallary",5200.00);
        list.add(staffMap);


        return list;
    }


    @RequestMapping(path = "/student{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

}
