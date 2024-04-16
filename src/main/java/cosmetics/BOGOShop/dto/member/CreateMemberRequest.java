package cosmetics.BOGOShop.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {

    @NotEmpty
    private String name;

    private Long age;
}
