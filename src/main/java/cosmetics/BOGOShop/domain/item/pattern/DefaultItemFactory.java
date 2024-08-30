package cosmetics.BOGOShop.domain.item.pattern;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.dto.item.ItemDto;

//공통 로직 관리(initializeItem) + 구체적인 전략 클래스(createSpecificItem -> Strategy 중)가 상속 받아 사용
public abstract  class DefaultItemFactory implements ItemFactory {

    @Override
    public Item createItem(ItemDto itemDto,Category category,SubCategory subCategory) {
        Item item = createSpecificItem();
        return initializeItem(item, itemDto, category, subCategory);
    }
    //아이템에 맞는 아이템생성전략을 선택
    protected abstract Item createSpecificItem();

    //모든 아이템에 관한 생성 초기화
    private Item initializeItem(Item item,ItemDto itemDto,Category category,SubCategory subCategory) {
        item.setCategory(category);
        item.setSubCategory(subCategory);
        item.setName(itemDto.getItemName());
        item.setPrice(itemDto.getPrice());
        item.setStockQuantity(itemDto.getStockQuantity());
        item.setBrandName(itemDto.getBrandName());

        return item;
    }
}
