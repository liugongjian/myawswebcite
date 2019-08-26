package com.myawscite.backend.repository;

import com.myawscite.backend.entity.UserInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepository extends CrudRepository<UserInfo,String> {



     Optional<UserInfo> findByEmail(String email);

     @Modifying
     @Transactional
     @Query("UPDATE UserInfo u SET u.password = ?1 WHERE u.email = ?2")
     void updatePwdByEmail(String password,String email);
}
