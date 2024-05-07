package cosmetics.BOGOShop.dto.member;

import cosmetics.BOGOShop.domain.Address;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {

    @NotEmpty
    private String name;

    private Long age;
    private String phone;

    @Embedded
    private Address address; //주소

}
