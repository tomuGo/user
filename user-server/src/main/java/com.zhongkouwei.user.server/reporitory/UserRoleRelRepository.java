package com.zhongkouwei.user.server.reporitory;

import com.zhongkouwei.user.server.model.UserRoleRel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRelRepository extends PagingAndSortingRepository<UserRoleRel,Integer> {

    List<UserRoleRel> findByUserId(Integer userId);

}
