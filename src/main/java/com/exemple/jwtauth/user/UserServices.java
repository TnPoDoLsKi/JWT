package com.exemple.jwtauth.user;

import com.exemple.jwtauth.config.MyUserDetails;
import com.exemple.jwtauth.utils.AuthResponse;
import com.exemple.jwtauth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder ;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyUserDetails myUserDetails;

    @Autowired
    JwtUtils jwtUtils;

    public void save(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public ResponseEntity<?> signIn(User user) throws Exception {

        Authentication authentication = null;

        try {
           authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(authentication.getName(),"",authentication.getAuthorities());
        System.out.println(userDetails);
        String jwt = jwtUtils.generateToken( userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
