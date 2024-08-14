package cosmetics.BOGOShop.dto.member;

import cosmetics.BOGOShop.domain.Address;
import cosmetics.BOGOShop.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateMemberResponse {
    private Long id;
    private String name;
    private String phone;
    private Address address; //주소


    static public UpdateMemberResponse toDto(Member member){
        return UpdateMemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .phone(member.getPhone())
                .address(member.getAddress())
                .build();
    }

}

