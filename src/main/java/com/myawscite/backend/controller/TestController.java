package com.myawscite.backend.controller;


import com.myawscite.backend.entity.RestResult;
import com.myawscite.backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//@RestController
//public class TestController {
//
//    @Autowired
//    private UserInfoService userInfoService;
//
//    @GetMapping(value = "/gettest/{testsort}/{testnum}")
//    public RestResult login(HttpServletRequest req, @PathVariable String testsort, @PathVariable String testnum){
//        return userInfoService.login(req,testsort,testnum);
//    }
//}
