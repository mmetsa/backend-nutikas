package com.nutikas.backendnutikas.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException;

}
