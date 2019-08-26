/**
 * 
 */
package com.myawscite.backend.interceptor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myawscite.backend.common.CommonConstants;
import com.myawscite.backend.common.UserContext;
import com.myawscite.backend.entity.UserInfo;
import com.myawscite.backend.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.google.common.base.Joiner;





@Component
public class AuthInterceptor implements HandlerInterceptor {
  
  private static final String TOKEN_COOKIE = "token";

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private UserRepository userRepository;




 @Override
 public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
 throws Exception {
     Map<String, String[]> map = req.getParameterMap();
     map.forEach((k,v) ->req.setAttribute(k, Joiner.on(",").join(v)));
     String requestURI = req.getRequestURI();
     if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {
        return true;
     }
     Cookie cookie_token = WebUtils.getCookie(req, TOKEN_COOKIE);
     Cookie cookie_username = WebUtils.getCookie(req,"username");
     if (StringUtils.isNoneBlank(cookie_token.getValue())&& StringUtils.isNoneBlank(cookie_username.getValue())) {
         String token_redis = redisTemplate.opsForValue().get(cookie_username.getValue()).toString();
         if (StringUtils.isBlank(token_redis) || !StringUtils.equals(token_redis,cookie_token.getValue())){
             return false;
         }
     }

     //System.out.println(Thread.currentThread());
    // UserInfo user = UserContext.getUser();
//     if(user == null || !user.getUsername().equals(cookie_username.getValue()) ){
////         return false;
////     };


 return true;
 }


 @Override
  public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
          ModelAndView modelAndView) throws Exception {
    String requestURI = req.getRequestURI();
    if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {
            return ;
        }
//        UserInfo user = UserContext.getUser();
//        if (user != null && StringUtils.isNoneBlank(user.getToken())) {
//            String token = requestURI.startsWith("logout")? "" : user.getToken();
//            Cookie cookie = new Cookie(TOKEN_COOKIE, token);
//            cookie.setPath("/");
//            cookie.setHttpOnly(false);
//            res.addCookie(cookie);
//    }
    
  }
  
  

  @Override
  public void afterCompletion(HttpServletRequest req, HttpServletResponse response, Object handler, Exception ex)
          throws Exception {
    UserContext.remove();
  }
}
