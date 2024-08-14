package cosmetics.BOGOShop.dto.member;

import cosmetics.BOGOShop.domain.Address;
import lombok.Data;

@Data
public class UpdateMemberRequest {

    private String name;
    private String phone;

    private Address address; //주소
}
