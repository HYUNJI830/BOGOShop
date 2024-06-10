package cosmetics.BOGOShop.controller;

import cosmetics.BOGOShop.domain.MemberStatus;
import cosmetics.BOGOShop.dto.Login.LoginRequest;
import cosmetics.BOGOShop.dto.Login.LoginResponse;
import cosmetics.BOGOShop.dto.member.LoginMemberDto;
import cosmetics.BOGOShop.service.LoginService;
import cosmetics.BOGOShop.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    //회원가입
    @PostMapping("/login/signup")
    public ResponseEntity<Void> signUp(@RequestBody LoginMemberDto loginMemberDto) {
        loginService.register(loginMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //로그인
    @PostMapping("/login/signin")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();

        LoginMemberDto userInfo = loginService.login(userId, password);

        if (userInfo == null) {
            return ResponseEntity.notFound().build();
        }

        Long id = userInfo.getId();
        LoginResponse loginResponse = LoginResponse.success(userInfo);

        if (userInfo.getStatus() == MemberStatus.ADMIN) {
            SessionUtil.setLoginAdminId(httpSession, id);
        } else {
            SessionUtil.setLoginMemberId(httpSession, id);
        }

        return ResponseEntity.ok(loginResponse);
    }
}
