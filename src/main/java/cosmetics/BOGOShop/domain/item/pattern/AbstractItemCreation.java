package cosmetics.BOGOShop.domain.item.pattern;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.repository.category.CategoryRepository;
import cosmetics.BOGOShop.repository.category.SubCategoryRepository;

//공통 로직 관리 + 구체적인 전략 클래스가 상속 받아 사용
public abstract  class AbstractItemCreation implements ItemCreationStrategy {

    @Override
    public Item createItem(ItemDto itemDto, Category category,SubCategory subCategory) {
        Item item = createSpecificItem();
        return initializeItem(item, itemDto, category, subCategory);
    }
    //하위 클래스에서 구체적인 아이템을 생성하는 메소드
    protected abstract Item createSpecificItem();

    private Item initializeItem(Item item, ItemDto itemDto,Category category , SubCategory subCategory) {
        item.setCategory(category);
        item.setSubCategory(subCategory);
        item.setName(itemDto.getItemName());
        item.setPrice(itemDto.getPrice());
        item.setStockQuantity(itemDto.getStockQuantity());
        item.setBrandName(itemDto.getBrandName());

        return item;
    }
}
