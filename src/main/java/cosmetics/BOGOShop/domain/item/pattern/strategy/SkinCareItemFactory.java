package cosmetics.BOGOShop.domain.item.pattern.strategy;

import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.SkinCare;
import cosmetics.BOGOShop.domain.item.pattern.DefaultItemFactory;
import cosmetics.BOGOShop.dto.item.ItemDto;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class SkinCareItemFactory extends DefaultItemFactory {
    @Override
    public Long getCategoryId() {
        return 2L;
    }
    @Override
    protected Item createSpecificItem(ItemDto itemDto) {
        SkinCare skinCareItem = new SkinCare();
        skinCareItem.setSkinType(determineSkinType(itemDto));
        return skinCareItem;
    }
    //스킨케어 제품의 피부 타입 결정 메서드
    private String determineSkinType(ItemDto itemDto){

        return "normal";
    }

    //스킨케어 제품의 유효기간 계산 메서드
//    private LocalDate calculateExpirationDate(){
//        return LocalDate.now().plusMonths(6);
//    }


}
