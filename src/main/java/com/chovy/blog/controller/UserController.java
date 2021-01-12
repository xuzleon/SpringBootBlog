package com.chovy.blog.controller;

import com.chovy.blog.annotation.LoginRequired;
import com.chovy.blog.entity.User;
import com.chovy.blog.service.UserService;
import com.chovy.blog.util.BlogUtil;
import com.chovy.blog.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    @Value("${blog.path.domain}")
    private String domain;

    @Value("${blog.path.upload}")
    private String uploadPath;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @LoginRequired  //加自定义注解，是否拦截
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }
    //图片上传

    @LoginRequired  //加自定义注解，是否拦截
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){
        if(headerImage==null){
            model.addAttribute("error","您还没有选择图片!");
            return "/site/setting";
        }
        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件格式有误!");
            return "/site/setting";
        }
        //生成随机文件名
        fileName = BlogUtil.generateUUID() + suffix;
        //确定文件存放路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败:"+e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常!",e);
        }
        //更新用户头像路径
        //http://localhost:8080/blog/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl=domain+contextPath+"/user/header/"+fileName;
        userService.updateHeader(user.getId(),headerUrl);
        return "redirect:/index";
    }
    @RequestMapping(path = "/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //服务器存放路径
        fileName = uploadPath + "/" + fileName;
        //文件名后缀
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/"+suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
                ){
            byte[] buffer = new byte[1024];
            int b=0;
            while ((b=fis.read(buffer))!=-1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败:"+e.getMessage());
        }

    }
    @RequestMapping(path = "/updatePassword",method = RequestMethod.POST)
    public String updatePassword(String password, String newPassword, String newPassword2, Model model,@CookieValue("ticket") String ticket){
        int id = hostHolder.getUser().getId();
        User user = userService.findUserById(id);
        System.out.println(user.toString());
        //验证旧密码
        String oldPassword = BlogUtil.md5(password+user.getSalt());
        if(!user.getPassword().equals(oldPassword)){
            model.addAttribute("passwordErr","原密码错误!");
            return "/site/setting";
        }
        if(!newPassword.equals(newPassword2)){
            model.addAttribute("checkErr","两次密码不一致!");
            return "/site/setting";
        }
        int rows = userService.updatePassword(id,BlogUtil.md5(newPassword+user.getSalt()));
        if(rows!=1){
            model.addAttribute("updateErr","修改密码失败!");
            return "/site/setting";
        }
        userService.logout(ticket);
        return "redirect:/login";
    }
}
