package com.se52.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.User;
import com.se52.repository.UserDAO;




@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
        	throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), mapAuthorities(user));
    }
    
    private List<GrantedAuthority> mapAuthorities(User user){
		return user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
				.collect(Collectors.toList());
	}

}