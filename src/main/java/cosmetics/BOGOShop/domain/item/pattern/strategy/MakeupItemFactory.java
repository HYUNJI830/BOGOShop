package cosmetics.BOGOShop.domain.item.pattern.strategy;

import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.domain.item.pattern.DefaultItemFactory;
import cosmetics.BOGOShop.dto.item.ItemDto;
import org.springframework.stereotype.Component;

@Component
public class MakeupItemFactory extends DefaultItemFactory {
    @Override
    public Long getCategoryId() {
        return 1L;
    }
    @Override
    protected Item createSpecificItem() {
        Makeup makeupItem = new Makeup();
        makeupItem.setColorPalette(determineColorPalette());
        makeupItem.setTextureType(selectTextureType());
        return makeupItem;
    }

    private String determineColorPalette() {
        return "Spring Colors";
    }

    private String selectTextureType() {
        return "Matte";
    }
}
