package com.sparta.myselectshop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.myselectshop.dto.LoginRequestDto;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
            /**

             ** attemptAuthentication 메서드에서 AuthenticationManager 가 Authenticate 를 진행하면 **

             ( Manager 가 Provider 에게 로그인 정보를 던지고 , Provider 가 UserDetailsService 를 찾아 loadUserByUsername 을 수행 )

             userDetailsServiceImpl 의 loadUserByUsername() 수행하며, DB 로부터 username 에 대한 User 객체를 조회

             ( 죄회된 User 객체와 객체에 대한 Password 일치한다면 Authentication 객체를 생성해 반환 < 정확히는 UserDetails 객체 ( Authentication 객체의 Principal 필드에 들어갈 ) > )

             * */
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}