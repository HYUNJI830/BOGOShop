package cosmetics.BOGOShop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id; //회원 아이디


    private String name; //이름

    private Long age; //나이

    private String phone; //연락처

    @Embedded
    private Address address; //주소

    @JsonIgnore //양방향 연관관계에는 모두 해야함
    @OneToMany(mappedBy = "member") //일대다
    private List<Order> orders = new ArrayList<>();

}
