package com.exemple.jwtauth.config;

import com.exemple.jwtauth.user.User;
import com.exemple.jwtauth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetails implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(s);

        if (user.isPresent()){
            return new org.springframework.security.core.userdetails.User(user.get().getUserName(),user.get().getPassword(),new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User Not found");
        }
    }
}
