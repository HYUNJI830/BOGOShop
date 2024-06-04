package cosmetics.BOGOShop.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.item.BodyCare;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyCareDto {
    //바디케어
    private Long bodyCareId;
    private String bodyCareName;
    private Long categoryId;


    public BodyCareDto() {
        // 기본 생성자
    }

    public BodyCareDto(Long bodyCareId, String bodyCareName) {
        this.bodyCareId = bodyCareId;
        this.bodyCareName = bodyCareName;
    }

    @QueryProjection
    public BodyCareDto(BodyCare bodyCare) {
        bodyCareId = bodyCare.getId();
        bodyCareName = bodyCare.getName();
        categoryId = bodyCare.getCategory().getId();
    }

}
