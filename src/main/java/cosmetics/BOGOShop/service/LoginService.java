package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.member.LoginMemberDto;
import cosmetics.BOGOShop.repository.MemberRepository;
import cosmetics.BOGOShop.repository.MemberRepositoryImpl;
import cosmetics.BOGOShop.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public void register(LoginMemberDto loginMemberDto){
        loginMemberDto.setPassword(SHA256Util.encryptSHA256(loginMemberDto.getPassword()));
        memberRepository.register(loginMemberDto);
    }

    //로그인
    public LoginMemberDto login(String userId, String password){
        log.info("userId: " + userId);
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        log.info("Encrypted Password: " + cryptoPassword);

        LoginMemberDto memberInfo = memberRepository.findMemberByIdAndPassword(userId,cryptoPassword);

        if (memberInfo == null) {
            log.info("Login failed: memberInfo is null");
        } else {
            log.info("Login successful: " + memberInfo.toString());
        }
        return memberInfo;
    }

}
