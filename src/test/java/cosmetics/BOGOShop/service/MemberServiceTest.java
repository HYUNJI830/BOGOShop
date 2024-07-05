package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.jwt.dto.JwtToken;
import cosmetics.BOGOShop.login.dto.Login.SignInDto;
import cosmetics.BOGOShop.login.dto.Login.SignUpDto;
import cosmetics.BOGOShop.dto.member.MemberDto;
import cosmetics.BOGOShop.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private SignUpDto signUpDto;
    private SignInDto signInDto;

    @BeforeEach
    public void setUp() {
        signUpDto = new SignUpDto();
        signUpDto.setUserId("testUser");
        signUpDto.setPassword("testPassword");

        signInDto = new SignInDto();
        signInDto.setUserId("testUser");
        signInDto.setPassword("testPassword");
    }

    @Test
    public void 회원가입() {
        // given
        // 회원가입 DTO 생성은 setUp()에서 미리 수행됨

        // when
        MemberDto savedMemberDto = memberService.signUp(signUpDto);

        // then
        assertNotNull(savedMemberDto);
        assertEquals(signUpDto.getUserId(), savedMemberDto.getUserId());
        //assertTrue(passwordEncoder.matches(signUpDto.getPassword(), savedMemberDto.getPassword()));
    }

    @Test
    public void 회원가입_중복아이디_예외() {
        // given
        memberService.signUp(signUpDto);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.signUp(signUpDto);
        });
    }

    @Test
    public void 로그인() {
        // given
        memberService.signUp(signUpDto);

        // when
        JwtToken jwtToken = memberService.signIn(signInDto.getUserId(), signInDto.getPassword());

        // then
        assertNotNull(jwtToken);
    }

    @Test
    public void 로그인_존재하지않는ID_예외() {
        // given
        String nonExistentUserId = "nonExistentUser";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.signIn(nonExistentUserId, signInDto.getPassword());
        });
    }

    @Test
    public void 로그인_잘못된비밀번호_예외() {
        // given
        memberService.signUp(signUpDto);
        String wrongPassword = "wrongPassword";

        // when & then
        assertThrows(BadCredentialsException.class, () -> {
            memberService.signIn(signUpDto.getUserId(), wrongPassword);
        });
    }
}