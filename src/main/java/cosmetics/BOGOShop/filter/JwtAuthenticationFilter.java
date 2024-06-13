package cosmetics.BOGOShop.filter;

import cosmetics.BOGOShop.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean  {

    private final JwtTokenProvider jwtTokenProvider;

    //주어진 HttpServletRequest에서 토큰 정보르 추출하는 역할

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //1. Request header에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request); //요청 헤더에서 JWT 토큰 추출

        //2. validateToken으로 토큰 유효성 검사
        if(token !=null && jwtTokenProvider.validateToken(token)){
            //토큰이 유효할 경우, 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); //요청 처리하는 동안 인증정보가 유지
        }
        chain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
    return null;
    }



}
