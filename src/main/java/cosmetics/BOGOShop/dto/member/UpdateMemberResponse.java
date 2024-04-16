package cosmetics.BOGOShop.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //모든 생성자 넘기는 어노테이션
public class UpdateMemberResponse {
    private Long id;
    private String name;
}

