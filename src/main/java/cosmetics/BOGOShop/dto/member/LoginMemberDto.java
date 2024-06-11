package cosmetics.BOGOShop.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import cosmetics.BOGOShop.domain.MemberStatus;
import lombok.Data;


import java.util.Date;

@Data
public class LoginMemberDto {


    private Long id;

    private String userId;
    private String password;

    private boolean isAdmin; //로직 만들어야함 status가 ADMIN이면 true
    private MemberStatus status;


    @QueryProjection
    public LoginMemberDto(Long id, String userId, String password, boolean isAdmin, MemberStatus status) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.isAdmin = isAdmin;
        this.status = status;
    }

}
