package cosmetics.BOGOShop.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BodyCareItemDto {
    //바디케어
    private Long bodyCareId;
    private String bodyCareName;

    //바디아이템
    private Long bodyItemId;
    private String bodyItemName;
    private int Price;
    private String brandName;



    @QueryProjection
    public BodyCareItemDto(Long bodyCareId, String bodyCareName, Long bodyItemId, String bodyItemName, int price, String brandName) {
        this.bodyCareId = bodyCareId;
        this.bodyCareName = bodyCareName;

        this.bodyItemId = bodyItemId;
        this.bodyItemName = bodyItemName;
        this.Price = price;
        this.brandName = brandName;

    }
}
