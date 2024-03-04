package org.example.jwt.Service;

import org.example.jwt.repository.Userrepos;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServices implements org.springframework.security.core.userdetails.UserDetailsService {

    protected final Userrepos userrepos;

    public UserDetailsServices(Userrepos userrepos) {
        this.userrepos = userrepos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userrepos.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException("usernamenot founf"));

    }
}
