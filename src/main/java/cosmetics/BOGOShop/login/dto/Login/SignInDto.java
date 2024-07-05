package cosmetics.BOGOShop.login.dto.Login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignInDto {
    private String userId;
    private String password;
}
