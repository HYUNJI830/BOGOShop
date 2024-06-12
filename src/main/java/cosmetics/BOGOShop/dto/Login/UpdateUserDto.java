package cosmetics.BOGOShop.dto.Login;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class UpdateUserDto {

    private String userId;

    private String beforePassword;

    private String afterPassword;

    public UpdateUserDto(String userId, String beforePassword, String afterPassword) {
        this.userId = userId;
        this.beforePassword = beforePassword;
        this.afterPassword = afterPassword;
    }
}
