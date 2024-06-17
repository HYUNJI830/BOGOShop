package cosmetics.BOGOShop.provider;

import cosmetics.BOGOShop.dto.Login.JwtToken;
import cosmetics.BOGOShop.dto.Login.LoginMemberDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    //application.yml에서 secret 값 가져와서 key에 저장
    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //Member 정보를 가지고 AcessToken, ReFreshToken 생성하는 메서드
    public JwtToken generateToken(Authentication authentication){
        //권한 가져오기
        String authorities = authentication.getAuthorities().stream() //사용자 권한 목록을 가져옴
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); //각 권한을 문자열로 변환하고 쉼표로 구분된 하나의 문자열로 결합
        long now = (new Date()).getTime();

        Date accessTokenExpire = new Date(now + 86400000); //24시간 후로 설정

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // JWT의 주체(subject)를 설정 (보통 사용자의 이름 혹은 id)
                .claim("auth",authorities) //사용자 권한 정보를 auth 클레임에 추가
                .setExpiration(accessTokenExpire) //액세스 토큰의 만료 시간을 설정
                .signWith(key, SignatureAlgorithm.HS256) //알고리즘 사용하여 서명
                .compact(); //JWT 문자열 생성

        //Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

        //JWT 토큰 객체 반환
        return JwtToken.builder()
                .grantType("Bearer") //토큰의 유형 설정
                .accessToken(accessToken) //생성된 액세스 토큰 설정
                .refreshToken(refreshToken) //생성된 리프레쉬 토큰 설정
                .build();
    }

    //JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken){
        //JWT 토큰 복호화
        Claims claims = parseClaims(accessToken);
        if(claims.get("auth") == null){
            throw  new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        //클레임에서 권한 정보 가져오기
        //Collection<? extends GrantedAuthority> > 권한 정보를 다양한 타입의 객체로 처리하기 위해
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))//토큰의 클레임에서 권한 정보를 가져옴, auth 클레임은 토큰에 저장된 권한 정보를 나타냄
                .map(SimpleGrantedAuthority::new) //가져온 권한 정보를 SimpleGrantedAuthority 객체로 변환하여 컬렉션에 추가
                .collect(Collectors.toList());
        //UserDetail 객체를 만들어서 Authentication return
        UserDetails principal = new User(claims.getSubject(),"",authorities); //UserDetails 객체를 생성하여 주체(subject)와 권한 정보(사용자이 식별자나 이메일 주소)
        return new UsernamePasswordAuthenticationToken(principal,"",authorities); //UsernamepasswordAuthenticationToken 객체 생성하여 주체와 권한정보를 포함한 인증 객체를 생성
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


}
