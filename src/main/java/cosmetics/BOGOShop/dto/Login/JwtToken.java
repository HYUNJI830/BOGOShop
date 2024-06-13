package cosmetics.BOGOShop.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType; //JWT 인증 타입
    private String accessToken;
    private String refreshToken;
}
