package com.bilgeadam.teknikservis.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bilgeadam.teknikservis.model.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private String activeProfiles;

    public JWTAuthorizationFilter(String activeProfiles) {
        this.activeProfiles = activeProfiles;
    }

    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            boolean isTokenOkay = false;
            String myToken = null;
            try {
                myToken = JWT.require(Algorithm.HMAC512("MY_SECRET_KEY".getBytes())).build().verify(token.replace("Bearer ", "")).getSubject();
                isTokenOkay = true;
            } catch (Exception e) {
                myToken = null;
                if (activeProfiles.equals("test")) {
                    filterChain.doFilter(request, response);
                } else {
                    //System.err.println(e.getMessage());
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Token exception => " + e.getMessage());
                }
            }
            if (myToken != null) {
                // user-USER
                // admin-ADMIN
                String username = myToken.split("-")[0];
                List<Role> auth = Arrays.asList(myToken.split("-")[1].split(",")).stream().map(item -> new Role(item)).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, auth);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);
            }
        } else {
            System.err.println("Token yok ama header gelmiş");
            // token yok, nasıl olsa security config 'den patlayacağım
            filterChain.doFilter(request, response);
        }
    }
}
