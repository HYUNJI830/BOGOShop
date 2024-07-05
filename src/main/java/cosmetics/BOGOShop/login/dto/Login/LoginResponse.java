package cosmetics.BOGOShop.login.dto.Login;

import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
    public LoginResponse(LoginStatus loginStatus) {
    }

    enum LoginStatus {
        SUCCESS, FAIL, DELETED
    }

    private LoginStatus result;
    private LoginMemberDto loginMemberDto;

    private static final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL);
    public static LoginResponse success(LoginMemberDto loginMemberDto){
        return new LoginResponse(LoginStatus.SUCCESS,loginMemberDto);
    }

}
