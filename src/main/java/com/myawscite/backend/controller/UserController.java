package com.myawscite.backend.controller;

import com.myawscite.backend.entity.RestResult;
import com.myawscite.backend.entity.UserInfo;
import com.myawscite.backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping(value = "/login")
    public RestResult login(HttpServletRequest req, HttpServletResponse res,@RequestParam String username, @RequestParam String password){
        return userInfoService.login(req,res,username,password);
    }

    @GetMapping(value = "/checkstatus")
    public RestResult checkStatus(){
        return RestResult.genSuccessResult("the user is login status");
    }

    @GetMapping(value = "/logout")
    public RestResult logout(String token){
        System.out.println(token);
        userInfoService.logout(token);
        return RestResult.genSuccessResult("the user is logout");
    }

    @GetMapping(value = "/getbackpwd")
    public RestResult getBackPwd(String email){

        return userInfoService.getBackPwd(email);
    }


}
