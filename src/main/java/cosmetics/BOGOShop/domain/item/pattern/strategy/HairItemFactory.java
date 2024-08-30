package cosmetics.BOGOShop.domain.item.pattern.strategy;

import cosmetics.BOGOShop.domain.item.HairItem;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.pattern.DefaultItemFactory;
import cosmetics.BOGOShop.dto.item.ItemDto;
import org.springframework.stereotype.Component;

@Component
public class HairItemFactory extends DefaultItemFactory {
    @Override
    public Long getCategoryId() {
        return 3L;
    }
    @Override
    protected Item createSpecificItem() {
        HairItem hairItem = new HairItem();
        hairItem.setHairType(determineHairType());
        return hairItem;
    }

    //헤어제품의 헤어타입
    private String determineHairType() {
        return "oily";
    }
}
