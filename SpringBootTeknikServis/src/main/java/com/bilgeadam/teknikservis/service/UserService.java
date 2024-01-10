package com.bilgeadam.teknikservis.service;

import com.bilgeadam.teknikservis.model.SystemUser;
import com.bilgeadam.teknikservis.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser myUser = userRepository.getByUsername(username);


        User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(myUser.getUsername());
        builder.password(myUser.getPassword());
        List<GrantedAuthority> userRoles = userRepository.getUserRoles(myUser.getUsername());
        builder.authorities(userRoles);

        return builder.build();

    }
}
