package com.myawscite.backend.service;


import com.google.common.collect.ImmutableMap;
import com.myawscite.backend.common.UserContext;
import com.myawscite.backend.entity.RestResult;
import com.myawscite.backend.entity.UserInfo;
import com.myawscite.backend.repository.UserRepository;
import com.myawscite.backend.utils.JwtHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;


    @Autowired
    private RedisTemplate redisTemplate;

    public RestResult login(HttpServletRequest req, HttpServletResponse res,String email, String password) {
        Optional<UserInfo> userInfo1 = userRepository.findByEmail(email);
        if(!userInfo1.isPresent()){
            return RestResult.genFailResult(401,"该用户不存在，请先注册");
        }else if(userInfo1.get().getPassword().equals(password)){
            String token = onLogin(userInfo1.get());
            userInfo1.get().setToken(token);
            //UserContext.setUser(userInfo1.get());
            System.out.println(Thread.currentThread());
            return RestResult.genSuccessResult(userInfo1.get());
        }else
            return RestResult.genFailResult(401,"密码错误，请重新输入");
    }


    private String onLogin(UserInfo user) {
        String token =  JwtHelper.genToken(ImmutableMap.of("id", user.getId(), "name", user.getEmail(),"ts", Instant.now().getEpochSecond()+""));
        return renewToken(token,user.getEmail());
    }
    private String renewToken(String token, String username) {
        redisTemplate.opsForValue().set(username, token);
        redisTemplate.expire(username, 30, TimeUnit.MINUTES);
        return token;
    }

    public void logout(String token) {
        Map<String, String> map = JwtHelper.verifyToken(token);
        redisTemplate.delete(map.get("name"));

    }

    public RestResult getBackPwd(String email) {
        Optional<UserInfo> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            String newRandomPwd = RandomStringUtils.randomAlphabetic(8);
            mailService.sendMail("大熊给您找回密码","您的新密码是："+ newRandomPwd,email);
            userRepository.updatePwdByEmail(newRandomPwd,email);
            return RestResult.genSuccessResult("密码重置成功",null);
        }
        return RestResult.genSuccessResult("您输入的密码有误",null);

    }

}
