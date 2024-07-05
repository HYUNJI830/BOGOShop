package cosmetics.BOGOShop.jwt.config;

import cosmetics.BOGOShop.jwt.dto.JwtToken;
import cosmetics.BOGOShop.jwt.service.CustomUserDetailsService;
import cosmetics.BOGOShop.jwt.exception.BlackLisToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Key key;
    private final RedisTemplate<String, String> redisTemplate;
    private final CustomUserDetailsService customUserDetailsService;

    //application.yml에서 secret 값 가져와서 key에 저장
    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, RedisTemplate<String, String> redisTemplate, CustomUserDetailsService userDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisTemplate = redisTemplate;
        this.customUserDetailsService = userDetailsService;
    }


    //Member 정보를 가지고 AcessToken, ReFreshToken 생성하는 메서드
    public JwtToken generateToken(Authentication authentication){
        //권한 가져오기
        String authorities = authentication.getAuthorities().stream() //사용자 권한 목록을 가져옴
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); //각 권한을 문자열로 변환하고 쉼표로 구분된 하나의 문자열로 결합

        //String authoritiesToString = authentication.getAuthorities().toString();
        String authoritiesName = authentication.getName();

        long now = (new Date()).getTime();

        long accessTokenExpirationTime = now + 1000*60*30; //30분
        long refreshTokenExpirationTime = now + 1000*60*60*6; //6시간

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authoritiesName) // JWT의 주체(subject)를 설정 (보통 사용자의 이름 혹은 id)
                .claim("auth",authorities) //사용자 권한 정보를 auth 클레임에 추가
                .setExpiration(new Date(now +accessTokenExpirationTime)) //액세스 토큰의 만료 시간을 설정
                .signWith(key, SignatureAlgorithm.HS256) //알고리즘 사용하여 서명
                .compact(); //JWT 문자열 생성

        //redis에 AccessToken 저장
        redisTemplate.opsForValue().set("ATK" + authoritiesName, accessToken, accessTokenExpirationTime, TimeUnit.MILLISECONDS);

        //Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authoritiesName)
                .claim("auth", authorities)
                .setExpiration(new Date(now + refreshTokenExpirationTime))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

        //redis에 RefreshToken 저장
        redisTemplate.opsForValue().set("RTK" + authoritiesName,refreshToken,refreshTokenExpirationTime,TimeUnit.MILLISECONDS);

        //JWT 토큰 객체 반환
        return JwtToken.builder()
                .grantType("Bearer") //토큰의 유형 설정
                .accessToken(accessToken) //생성된 액세스 토큰 설정
                .refreshToken(refreshToken) //생성된 리프레쉬 토큰 설정
                .build();
    }

    //JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        if(claims.get("auth") == null){
            throw  new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get("auth").toString().split(","))//토큰의 클레임에서 권한 정보를 가져옴, auth 클레임은 토큰에 저장된 권한 정보를 나타냄
            .map(SimpleGrantedAuthority::new) //가져온 권한 정보를 SimpleGrantedAuthority 객체로 변환하여 컬렉션에 추가
            .collect(Collectors.toList());
        UserDetails principal= new User(claims.getSubject(),"",authorities);
        //UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    //JWT 액세스 토큰을 파싱하여 Claims 객체를 반환하는 메소드
    //JWT 토큰은 기본적으로 클레임(Claims)을 포함하는 JSON 객체를 통해 사용자 정보 추출
    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key) //JWT 서명한 비밀 키를 설정, 키는 토근을 검증하는 데 사용
                    .build() //설정된 값을 바탕으로 JwtParser 객체 생성
                    .parseClaimsJws(accessToken) //accessToken 파싱하여 Jws<Claims> 반환
                    .getBody(); //JWT의 본문(Claims)반환
        } catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
    //JWT 토큰의 만료시간
    public Long getExpiration(String accessToken){
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer")){
            return token.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request){
        String refreshToken = request.getHeader("refreshToken");
        if(StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer")){
            return refreshToken.substring(7);
        }
        return null;
    }

    public void validateBlackListToken(String accessToken){
        String blackList = redisTemplate.opsForValue().get(accessToken);
        if(StringUtils.hasText(blackList)){
            throw new BlackLisToken("로그아웃된 사용자 입니다.");
        }
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()  //토큰의 서명 키를 설정하고, 예외처리 통해 토큰의 유효성 여부 판단
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) { //토큰이 올바른 형식이 아니거나 클레임이 비어있는 경우
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
    //accessToken 만료시 토큰 재발급
    public static JwtToken setToken(JwtToken jwtTokenDto){
        return jwtTokenDto;
    }


}
