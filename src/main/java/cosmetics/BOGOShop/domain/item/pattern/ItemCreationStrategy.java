package cosmetics.BOGOShop.domain.item.pattern;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.repository.category.CategoryRepository;
import cosmetics.BOGOShop.repository.category.SubCategoryRepository;

public interface ItemCreationStrategy {
    Long getCategoryId(); //각 전력에 대한 카테고리 ID 반환
    Item createItem(ItemDto itemDto, Category category, SubCategory subCategory);
}
