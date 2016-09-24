package com.gavin.service.impl;

import com.gavin.entity.User;
import com.gavin.entity.UserAuthority;
import com.gavin.enums.AuthorityEnums;
import com.gavin.exception.UsernameExistedException;
import com.gavin.repository.UserRepository;
import com.gavin.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    @Transactional
    public void createUser(User user) {
        if (null == userRepository.findByUserName(user.getUserName())) {
            throw new UsernameExistedException("user name " + user.getUserName() + "is already existing. ");
        }

        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setAuthority(AuthorityEnums.AUTH_USER_WRITE);
        user.addUserAuthority(userAuthority);
        userRepository.save(user);
    }
}
