package com.zhongkouwei.user.server.reporitory;

import com.zhongkouwei.user.common.model.RoleInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleInfoRepository extends PagingAndSortingRepository<RoleInfo,Integer> {

}
