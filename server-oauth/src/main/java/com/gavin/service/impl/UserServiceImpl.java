package com.gavin.service.impl;

import com.gavin.entity.User;
import com.gavin.entity.UserAuthority;
import com.gavin.enums.AuthorityEnums;
import com.gavin.repository.UserRepository;
import com.gavin.security.DemoUserDetails;
import com.gavin.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("searching user");
        User user = userRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException(username));

        return new DemoUserDetails(user);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setAuthority(AuthorityEnums.AUTH_USER_WRITE);
        user.addUserAuthority(userAuthority);
        userRepository.save(user);
    }
}
