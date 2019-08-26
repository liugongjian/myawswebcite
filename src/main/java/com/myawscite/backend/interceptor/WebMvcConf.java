package com.myawscite.backend.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConf implements WebMvcConfigurer {

  @Autowired
  private AuthInterceptor authInterceptor;
  
  @Autowired
  private AuthActionInterceptor authActionInterceptor;
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor).excludePathPatterns("/user/login").excludePathPatterns("/static").excludePathPatterns("/user/logout").addPathPatterns("/**")
    .excludePathPatterns("/getbackpwd");
    registry.addInterceptor(authActionInterceptor).excludePathPatterns("/static")
         .addPathPatterns("/user/checkstatus");

  }
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")//拦截所有的url
        .allowedOrigins("*")// 放行哪些原始域，比如"http://domain1.com,https://domain2.com"
        .allowCredentials(true)// 是否发送Cookie信息
        .allowedMethods("GET", "POST", "PUT", "DELETE") // 放行哪些原始域(请求方式)
        .allowedHeaders("*");// 放行哪些原始域(头部信息)
  }
  

}
