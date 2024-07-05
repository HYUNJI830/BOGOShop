package cosmetics.BOGOShop.jwt.config;

import cosmetics.BOGOShop.jwt.dto.JwtToken;
import cosmetics.BOGOShop.jwt.exception.CheckTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //1. Request header에서 JWT 토큰 추출
        String accessToken = jwtTokenProvider.resolveToken(request); //요청 헤더에서 JWT 토큰 추출
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        //로그아웃된 토큰인지 확인
        if (StringUtils.hasText(accessToken)) {
            jwtTokenProvider.validateBlackListToken(accessToken);
        }

    //2. validateToken으로 토큰 유효성 검사
    if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
        //토큰이 유효할 경우, 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 저장
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        //가장 최신 토큰인지 확인
        String ATK = redisTemplate.opsForValue().get("ATK" + authentication.getName());
        if (!ATK.equals(accessToken)) {
            throw new CheckTokenException("최신 토큰을 사용하세요");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication); //요청 처리하는 동안 인증정보가 유지
        //accessToken 만료시 refreshToken 검증
    } else if (StringUtils.hasText(refreshToken) && !jwtTokenProvider.validateToken(accessToken)) {
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        //가장 최신 토큰인지 확인
        String RTK = redisTemplate.opsForValue().get("RTK" + authentication.getName());
        if (!RTK.equals(refreshToken)) {
            throw new CheckTokenException("최신 refresh토큰을 사용하세요");
        }
        //accessToken,refreshToken 모두 재발급(refreshtoken 발급마다 redis 저장해줘서 update 로직 필요 X)
        JwtToken tokenDto = jwtTokenProvider.generateToken(authentication);
        JwtTokenProvider.setToken(tokenDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

        chain.doFilter(request,response);
    }




}
