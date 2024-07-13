package com.sparta.springauth.filter;

import com.sparta.springauth.entity.User;
import com.sparta.springauth.jwt.JwtUtil;
import com.sparta.springauth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

//@Component
@Slf4j(topic = "AuthFilter")
@Order(2)
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();

        if (NoCheckFilter(url)) {
            // 회원가입, 로그인 관련 API 인증 필요없이 요청 진행
            log.info("인증 처리 하지 않는 URL: {}", url);
            chain.doFilter(request, response);
        } else {
            // 나머지 API 요청 인증 처리 진행

            log.info("인증 처리 하는 URL: {}", url);
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(req);
            if (StringUtils.hasText(tokenValue)) {
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // JWT 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token error");
                }

                // JWT 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("Not Found User")
                );

                request.setAttribute("user", user);
                chain.doFilter(request, response);
            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }

    private static boolean NoCheckFilter(String url) {
        return StringUtils.hasText(url) && (url.startsWith("/api/user") || url.startsWith("/css") || url.startsWith("/js") || url.equals("/"));
    }
}
