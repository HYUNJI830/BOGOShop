package cosmetics.BOGOShop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id; //회원 식별번호

    private String userId; //회원 아이디
    private String password;//회원 비밀번호

    private String name; //이름

    private Long age; //나이

    private String phone; //연락처

    private boolean isAdmin; //운영자 여부

    private MemberStatus status;// GUEST, MEMBER ,ADMIN


    @Embedded
    private Address address; //주소

    @JsonIgnore //양방향 연관관계에는 모두 해야함
    @OneToMany(mappedBy = "member") //일대다
    private List<Order> orders = new ArrayList<>();



    //==비즈니스 로직==//
    public boolean isAdmin(MemberStatus status){
        if(status == MemberStatus.ADMIN){

            return isAdmin = true;
        }
        return isAdmin = false;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                //.map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
