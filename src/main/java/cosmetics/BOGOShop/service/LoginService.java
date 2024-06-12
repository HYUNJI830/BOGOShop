package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.Login.LoginMemberDto;
import cosmetics.BOGOShop.repository.MemberRepository;
import cosmetics.BOGOShop.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = memberRepository.register(loginMemberDto);
        member.isAdmin(loginMemberDto.getStatus());
    }

    //로그인
    public LoginMemberDto login(String userId, String password){
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        LoginMemberDto memberInfo = memberRepository.findMemberByIdAndPassword(userId,cryptoPassword);

        if (memberInfo == null) {
            log.info("Login failed: memberInfo is null");
        } else {
            log.info("Login successful: " + memberInfo.toString());
        }
        return memberInfo;
    }

    public void updatePassword(String userId,String beforePassword, String afterPassword){
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        LoginMemberDto loginMemberInfo = memberRepository.findMemberByIdAndPassword(userId,cryptoPassword);

        if(loginMemberInfo != null){
            loginMemberInfo.setPassword(SHA256Util.encryptSHA256(afterPassword));
            memberRepository.updatePassword(loginMemberInfo);
        } else{
            log.error("updatePasswrod ERROR! {}", loginMemberInfo);
        }

    }

}
