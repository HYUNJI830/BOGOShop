package cosmetics.BOGOShop.dto.Login;

import cosmetics.BOGOShop.domain.Address;
import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.MemberStatus;
import cosmetics.BOGOShop.domain.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String userId; //회원 아이디

    private String password;//회원 비밀번호

    private String name; //이름

    private Long age; //나이

    private String phone; //연락처

    private boolean isAdmin; //운영자 여부

    private MemberStatus status;// GUEST, MEMBER ,ADMIN

    private Address address; //주소

    private List<Role> roles = Collections.singletonList(Role.USER);


    public Member toEntity(String encodedPassword){
        return Member.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(name)
                .age(age)
                .phone(phone)
                .isAdmin(isAdmin)
                .status(status)
                .address(address)
                .roles(roles)
                .build();
    }
}
