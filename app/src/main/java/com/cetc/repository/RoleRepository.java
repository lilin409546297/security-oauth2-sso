package com.cetc.repository;

import com.cetc.domain.Role;

import java.util.List;

public interface RoleRepository extends BaseRepository<Role, Long> {

    /**
     * 通过角色类型查询角色
     * @param roleType
     * @return
     */
    Role findByRoleType(String roleType);
}
