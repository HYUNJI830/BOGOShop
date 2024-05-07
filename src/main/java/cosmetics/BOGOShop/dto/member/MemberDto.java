package cosmetics.BOGOShop.dto.member;

import cosmetics.BOGOShop.domain.Address;
import cosmetics.BOGOShop.domain.Member;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private String name;

    private Long age;
    private String phone;

    @Embedded
    private Address address; //주소

    public MemberDto(Member member){
        name = member.getName();
        age = member.getAge();
        phone = member.getPhone();
        address = member.getAddress();
    }


}
