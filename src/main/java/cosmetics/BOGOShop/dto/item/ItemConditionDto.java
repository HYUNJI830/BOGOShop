package cosmetics.BOGOShop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemConditionDto {

    private int stockQuantity;
    private int priceMin;
    private int priceMax;

}
