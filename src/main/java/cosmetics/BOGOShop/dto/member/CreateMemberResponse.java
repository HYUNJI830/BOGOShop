package cosmetics.BOGOShop.dto.member;

import lombok.Data;

@Data
public class CreateMemberResponse {
    private Long id;
    public CreateMemberResponse(Long id){
        this.id = id;
    }
}
