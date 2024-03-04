package org.example.jwt.Filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jwt.Service.JwtService;
import org.example.jwt.Service.UserDetailsServices;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    protected final JwtService jwtService;
    protected  final UserDetailsServices userdetails;


    public JwtFilter(JwtService jwtService, UserDetailsServices userdetails) {
        this.jwtService = jwtService;
        this.userdetails = userdetails;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authheader=request.getHeader("Authorization");

        if(authheader==null || !authheader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String token=authheader.substring(7);
        String usernmae=jwtService.extractusername(token);

        if(usernmae!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails= userdetails.loadUserByUsername(usernmae);

            if(jwtService.isvalid(token,userDetails)){
                UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authtoken);
            };


        }

        filterChain.doFilter(request,response);

    }
}
