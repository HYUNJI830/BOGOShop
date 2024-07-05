package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.jwt.dto.JwtToken;
import cosmetics.BOGOShop.login.dto.Login.SignUpDto;
import cosmetics.BOGOShop.dto.member.MemberDto;
import cosmetics.BOGOShop.jwt.config.JwtTokenProvider;
import cosmetics.BOGOShop.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final & @AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * * 회원가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMemberName(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원 검증
    private void validateDuplicateMemberName(Member member){
        List<Member> findMemberNames = memberRepository.findByName(member.getName());

        if(!findMemberNames.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * * 전체 회원 조회
     */
    public List <Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }

    /**
     * JWT 서비스
     */
    @Transactional
    public MemberDto signUp(SignUpDto signUpDto){
        //아이디 중복 체크
        Optional<Member> isExistUserId = memberRepository.findByUserId(signUpDto.getUserId());
        if (isExistUserId.isPresent()){
            throw new IllegalArgumentException("이미 사용 중인 ID 입니다.");
        }
        //Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
//        List<String> roles = new ArrayList<>();
//        roles.add("USER"); //USER 권한 부여
        Member member = memberRepository.save(signUpDto.toEntity(encodedPassword));
        return MemberDto.toDto(member);
    }


    @Transactional
    public JwtToken signIn(String userId, String password) {
        //아이디 존재 여부
        Optional<Member> isExistUserId = memberRepository.findByUserId(userId);
        if(!isExistUserId.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 ID 입니다.");
        }
        // 1. userID + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }
    public void logout(HttpServletRequest request, String userId){
        String accessToken = jwtTokenProvider.resolveToken(request);

        Long expiration = jwtTokenProvider.getExpiration(accessToken);

        redisTemplate.opsForValue().set(
                accessToken,
                "logout",
                expiration,
                TimeUnit.MILLISECONDS
        );

        //리프레쉬 토큰 삭제
        redisTemplate.delete("RTK"+userId);
    }

}
