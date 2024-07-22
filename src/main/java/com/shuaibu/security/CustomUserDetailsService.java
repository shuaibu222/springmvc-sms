package com.shuaibu.security;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.UserModel;
import com.shuaibu.repository.StudentRepository;
import com.shuaibu.repository.UserRepository;
import com.shuaibu.service.impl.StaffImpl;
import com.shuaibu.service.impl.StudentImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        if (!user.getIsActive().equals("True")) {
            throw new UsernameNotFoundException("User is not active");
        }

        // Convert roles from UserModel to GrantedAuthority
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
