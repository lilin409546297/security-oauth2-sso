package com.cetc.repository;

import com.cetc.domain.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

}
