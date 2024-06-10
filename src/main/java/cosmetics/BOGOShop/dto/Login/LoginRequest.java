package cosmetics.BOGOShop.dto.Login;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
