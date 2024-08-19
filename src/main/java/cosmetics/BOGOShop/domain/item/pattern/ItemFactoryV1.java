package cosmetics.BOGOShop.domain.item.pattern;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.repository.category.CategoryRepository;
import cosmetics.BOGOShop.repository.category.SubCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemFactoryV1 {

    private final Map<Long, ItemCreationStrategy> strategyMap = new HashMap<>();
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    //ItemFactoryV1의 생성자에서 등록된 
    public ItemFactoryV1(List<ItemCreationStrategy> strategyList, CategoryRepository categoryRepository,SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;

        for(ItemCreationStrategy strategy : strategyList) {
            Long key = strategy.getCategoryId();
            strategyMap.put(key,strategy);
            System.out.println("등록된 전략: " + key + " -> " + strategy.getClass().getSimpleName());
        }
    }

    //카테고리별 상품 등록
    public Item createItem(ItemDto itemDto) {
        //String categoryName = itemDto.getCategoryName().toLowerCase();
        //ItemCreationStrategy strategy = strategyMap.get(categoryName);
        Category category = categoryRepository.findByID(itemDto.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.: " + itemDto.getCategoryId());
        }

        SubCategory subCategory = subCategoryRepository.findById(itemDto.getSubCategoryId());
        if (subCategory == null) {
            throw new IllegalArgumentException("존재하지 않는 서브카테고리입니다.: " + itemDto.getSubCategoryId());
        }
        ItemCreationStrategy strategy = strategyMap.get(category.getId());
        if (strategy == null) {
            throw new IllegalArgumentException("해당 카테고리에 대한 전략이 없습니다.: " + category.getName());
        }
        return strategy.createItem(itemDto, category, subCategory);
    }

}
