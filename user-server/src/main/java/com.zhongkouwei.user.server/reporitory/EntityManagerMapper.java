package com.zhongkouwei.user.server.reporitory;

import com.zhongkouwei.user.common.model.RoleInfo;
import com.zhongkouwei.user.common.model.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EntityManagerMapper {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RoleInfo> findRolesByUserId(Integer userId) {
        String sql = "select a.* from role a " +
                "left join user_role_rel b on a.role_id = b.role_id " +
                "where b.user_id = '" + userId + "';";
        Query query = entityManager.createNativeQuery(sql, RoleInfo.class);
        List<RoleInfo> roleInfos = query.getResultList();
        return roleInfos;
    }

    public UserInfo getLoginUserInfo(UserInfo userInfo) {
        StringBuffer sql = new StringBuffer("select * from user where 1 =1 ");
        if (!StringUtils.isEmpty(userInfo.getUsername())) {
            sql.append(" and username = '" + userInfo.getUsername() + "'");
        }
        if (!StringUtils.isEmpty(userInfo.getEmail())) {
            sql.append(" and email ='" + userInfo.getEmail() + "'");
        }
        sql.append(" and password ='" + userInfo.getPassword() + "'");
        Query query = entityManager.createNativeQuery(sql.toString(), UserInfo.class);
        List<UserInfo> user = query.getResultList();
        Assert.isTrue(!CollectionUtils.isEmpty(user) && user.size() == 1, "账号或密码错误");
        return user.get(0);
    }

}
