package cosmetics.BOGOShop.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import cosmetics.BOGOShop.domain.Address;
import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.Role;
import jakarta.persistence.Embedded;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private String userId; //회원 아이디
    private String password;// 테스트용

    private String name;
    private Long age;
    private String phone;

    @Embedded
    private Address address; //주소

    @JsonProperty("roles")
    private List<Role> roles = new ArrayList<>();

    private boolean isAdmin; //운영자 여부



    public MemberDto(Member member){
        userId = member.getUserId();
        password = member.getPassword();
        name = member.getName();
        age = member.getAge();
        phone = member.getPhone();
        address = member.getAddress();
        roles = member.getRoles();
        isAdmin = member.isAdmin();
    }

    static public MemberDto toDto(Member member){
        return MemberDto.builder()
                .userId(member.getUserId())
                .password(member.getPassword()) // 패스워드 추가
                .name(member.getName())
                .age(member.getAge())
                .phone(member.getPhone())
                .address(member.getAddress())
                .roles(member.getRoles())
                .isAdmin(member.isAdmin())
                .build();
    }
    public Member toEntity(){
        return Member.builder()
                .userId(userId)
                .name(name)
                .age(age)
                .phone(phone)
                .address(address)
                .roles(roles)
                .isAdmin(isAdmin)
                .build();
    }

}
