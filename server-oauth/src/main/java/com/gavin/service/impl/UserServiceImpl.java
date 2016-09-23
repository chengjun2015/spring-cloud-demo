package com.gavin.service.impl;

import com.gavin.entity.User;
import com.gavin.repository.UserRepository;
import com.gavin.security.DemoUserDetails;
import com.gavin.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException(username));

        return new DemoUserDetails(user);
    }
}
