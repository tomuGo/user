package com.zhongkouwei.user.server.reporitory;

import com.zhongkouwei.user.common.model.UserInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserInfo,Integer> {
    List<UserInfo> findByUsername(String userName);
    List<UserInfo> findByEmail(String email);

    @Query(value = "select * from user where username = :account or email =:account",nativeQuery = true)
    List<UserInfo> findByUserNameOrEmail(String account);

    @Transactional
    @Modifying
    @Query(value="UPDATE UserInfo xe SET xe.password= :password WHERE xe.userId= :userId")
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);

    @Transactional
    @Modifying
    @Query(value="UPDATE UserInfo xe SET xe.picUrl= :picUrl WHERE xe.userId= :userId")
    int updatePicUrl(@Param("userId") Integer userId, @Param("picUrl") String picUrl);
}
