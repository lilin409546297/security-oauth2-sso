package com.cetc.task;

import com.cetc.domain.Role;
import com.cetc.domain.User;
import com.cetc.repository.RoleRepository;
import com.cetc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 初始化用户.
 */
@Component
public class InitUserTask {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void execute() {
        //1、初始化默认角色信息
        Iterator<Role> iterator = roleRepository.findAll().iterator();
        if (!iterator.hasNext()) {
            Role admin = new Role();
            admin.setRoleName("管理员");
            admin.setRoleType(Role.RoleType.ADMIN);
            Role user = new Role();
            user.setRoleName("用户");
            user.setRoleType(Role.RoleType.USER);
            List<Role> roles = new ArrayList<>();
            roles.add(admin);
            roles.add(user);
            roleRepository.saveAll(roles);
        }
        //2、初始化默认用户
        User admin = userRepository.findByUsername("appapp");
        if (admin == null) {
            User user = new User();
            user.setUsername("appapp");
            user.setPassword(passwordEncoder.encode("appapp"));
            //加入管理员角色
            Role role = roleRepository.findByRoleType(Role.RoleType.ADMIN);
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }
}
