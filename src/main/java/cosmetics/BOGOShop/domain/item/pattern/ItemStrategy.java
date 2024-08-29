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
public class ItemStrategy {

    private final Map<Long, ItemFactory> factoryMap = new HashMap<>();
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    //ItemStrategy 생성자에서 ItemFactory 인터페이스를 구현한 모든 빈을 자동으로 리스트형식 주입
    public ItemStrategy(List<ItemFactory> strategyList, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;

        for(ItemFactory strategy : strategyList) {
            Long key = strategy.getCategoryId();
            factoryMap.put(key,strategy);
            System.out.println("등록된 전략: " + key + " -> " + strategy.getClass().getSimpleName());
        }
    }

    //카테고리별 상품 등록
    public Item createItem(ItemDto itemDto) {
        Category category = categoryRepository.findByID(itemDto.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.(카테고리 아이디): " + itemDto.getCategoryId());
        }
        SubCategory subCategory = subCategoryRepository.findById(itemDto.getSubCategoryId());
        if (subCategory == null) {
            throw new IllegalArgumentException("존재하지 않는 서브카테고리입니다.(서브카테고리 아이디): " + itemDto.getSubCategoryId());
        }
        ItemFactory factory = factoryMap.get(category.getId()); //주입된 팩토리들을 카테고리ID로 관리
        if (factory == null) {
            throw new IllegalArgumentException("카테고리와 맞는 공장이 없습니다.(카테고리 이름) : " + category.getName());
        }
        return factory.createItem(itemDto, category, subCategory);
    }

}
