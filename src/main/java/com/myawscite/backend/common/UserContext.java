package com.myawscite.backend.common;

import com.myawscite.backend.entity.UserInfo;

public class UserContext {
  private static final ThreadLocal<UserInfo> USER_HOLDER = new ThreadLocal<>();
  
  public static void setUser(UserInfo user){
    USER_HOLDER.set(user);
  }
  
  public static void remove() {
    USER_HOLDER.remove();
  }
  
  public static UserInfo getUser() {
    return USER_HOLDER.get();
  }

}
