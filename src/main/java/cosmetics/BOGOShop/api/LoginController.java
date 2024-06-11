package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.MemberStatus;
import cosmetics.BOGOShop.dto.Login.LoginRequest;
import cosmetics.BOGOShop.dto.Login.LoginResponse;
import cosmetics.BOGOShop.dto.member.LoginMemberDto;
import cosmetics.BOGOShop.service.LoginService;
import cosmetics.BOGOShop.utils.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest,HttpServletRequest request) {
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();

        LoginMemberDto userInfo = loginService.login(userId, password);

        if (userInfo == null) {
            return ResponseEntity.notFound().build();
        }

        LoginResponse loginResponse = LoginResponse.success(userInfo);

        HttpSession httpSession = request.getSession();

        //이결로 대체 가능
        if(userInfo.getStatus() == MemberStatus.ADMIN){
            httpSession.setAttribute(SessionConst.LOGIN_ADMIN_ID,userInfo);
        }else {
            httpSession.setAttribute(SessionConst.LOGIN_MEMBER_ID,userInfo);
        }

//        if (userInfo.getStatus() == MemberStatus.ADMIN) {
//            SessionUtil.setLoginAdminId(httpSession, id); //세션에 로그인 정보 보관(Admin)
//        } else {
//            SessionUtil.setLoginMemberId(httpSession, id);//세션에 로그인 정보 보관(Member)
//        }

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping ("/login/signout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
