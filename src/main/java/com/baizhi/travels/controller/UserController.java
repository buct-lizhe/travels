package com.baizhi.travels.controller;


import com.baizhi.travels.entity.Result;
import com.baizhi.travels.entity.User;
import com.baizhi.travels.service.UserService;
import com.baizhi.travels.utils.ValidateImageCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController  //省去了在每个方法前加@responseBody即可自动转化为json
@RequestMapping("user")
@CrossOrigin //允许跨域
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    //用户登录
    @RequestMapping("login")
    public Result login(@RequestBody User user , HttpServletRequest request ) {
        Result result = new Result();
        log.info("user: " + user);
        User selectUserByUser = userService.selectUserByUser(user);
        log.info("selectUserByUser：" + selectUserByUser);
        try {
            if (selectUserByUser != null) {
                request.getServletContext().setAttribute(selectUserByUser.getId(),selectUserByUser);  //将用户标记存在servletContext中
                result.setUserId( selectUserByUser.getId() );
                result.setMsg("登陆成功！！！");
            } else {
                throw new RuntimeException("用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(e.getMessage()).setState(false);
        }
        return result;
    }


    //用户注册
    @PostMapping("register")
    public Result  register(String code, String key, @RequestBody User user, HttpServletRequest request) {
        Result result = new Result();
        log.info("接收的验证码: " + code);
        log.info("接收的验证码的key: " + key);
        log.info("接收到user对象: " + user);
        //验证验证码
        String keyCode = (String) request.getServletContext().getAttribute(key);
        log.info(keyCode);
        try {
            if (code.equalsIgnoreCase(keyCode)) {
                //注册用户
                userService.register(user);
                result.setMsg("注册成功!!!");
            } else {
                throw new RuntimeException("验证码错误!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(e.getMessage()).setState(false);
        }
        return result;
    }

    //生成验证码
    @GetMapping( "getImage" )
    public Map<String , String> getImage(HttpServletResponse httpServletResponse , HttpServletRequest request  ) throws IOException {
        //System.out.println("111111111111");
        Map<String,String> result = new HashMap<>();
        //生成验证码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        //将验证码放入session
        String key =  new SimpleDateFormat("yyyyMMddHHmmss").format( new Date());
        request.getServletContext().setAttribute( key , securityCode );
        //生成图片
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        //进行base64调用
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image , "png" , bos );
        String string = Base64Utils.encodeToString(bos.toByteArray());
        result.put( "key" , key );
        result.put("image" , string);
        return result; //返回的是一个json格式的内容
    }
}
